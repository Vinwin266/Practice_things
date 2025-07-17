package main;
import main.Ticket;

import java.util.Date;
public class Payment {
    private String paymentId;
    private double amount;
    private Date paymentTime;
    private Ticket ticket;

    public Payment(String paymentId, double amount, Ticket ticket) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentTime = new Date(); // Set payment time to current time
        this.ticket = ticket;
    }

    public boolean processPayment() {
        // Payment processing logic
        return true; // Assuming payment is successful
    }

    // Other payment-related methods
}