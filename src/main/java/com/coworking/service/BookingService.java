package com.coworking.service;

import com.coworking.dto.BookingRequest;
import com.coworking.entity.Booking;
import com.coworking.entity.User;
import com.coworking.payment.*;
import com.coworking.repository.BookingRepository;
import com.coworking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    public Booking createBooking(BookingRequest bookingRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        double totalPrice = calculatePrice(bookingRequest.getStartDate(), bookingRequest.getEndDate());

        boolean paymentSuccess = processPayment(
                bookingRequest.getPaymentMethod(),
                totalPrice,
                bookingRequest.getPaymentDetails());

        if (!paymentSuccess) {
            throw new RuntimeException("Payment failed");
        }

        Booking booking = new Booking(
                user,
                bookingRequest.getWorkspaceId(),
                bookingRequest.getStartDate(),
                bookingRequest.getEndDate(),
                totalPrice,
                bookingRequest.getPaymentMethod(),
                "CONFIRMED");

        return bookingRepository.save(booking);
    }

    private double calculatePrice(Date startDate, Date endDate) {
        long diffInMillis = endDate.getTime() - startDate.getTime();
        double hours = diffInMillis / (1000.0 * 60 * 60);
        return hours * 10.0;
    }

    private boolean processPayment(String paymentMethod, double amount, Map<String, String> paymentDetails) {
        PaymentStrategy strategy;

        switch (paymentMethod.toLowerCase()) {
            case "creditcard":
                strategy = new CreditCardPayment(
                        paymentDetails.get("cardNumber"),
                        paymentDetails.get("expiryDate"),
                        paymentDetails.get("cvv"));
                break;
            case "paypal":
                strategy = new PayPalPayment(
                        paymentDetails.get("email"),
                        paymentDetails.get("password"));
                break;
            default:
                throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
        }

        PaymentContext paymentContext = new PaymentContext(strategy);
        return paymentContext.executePayment(amount);
    }
}