package com.coworking.payment;

public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    private String expiryDate;
    private String cvv;

    public CreditCardPayment(String cardNumber, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public boolean pay(double amount) {
        // Implement actual credit card payment logic
        System.out.println("Processing credit card payment of $" + amount);
        return true; // Simulate successful payment
    }
}