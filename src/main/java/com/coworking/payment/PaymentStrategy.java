package com.coworking.payment;

public interface PaymentStrategy {
    boolean pay(double amount);
}