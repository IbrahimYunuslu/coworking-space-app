package com.coworking.payment;

public class PayPalPayment implements PaymentStrategy {
    private String email;
    private String password;

    public PayPalPayment(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean pay(double amount) {
        // Implement actual PayPal payment logic
        System.out.println("Processing PayPal payment of $" + amount);
        return true; // Simulate successful payment
    }
}