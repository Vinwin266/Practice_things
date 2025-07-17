package main;
import main.Vehicle;
import main.ParkingSpot;

import java.util.*;
public class Ticket {
    private String ticketId;
    private Vehicle vehicle;
    private ParkingSpot spot;
    private Date entryTime;
    private Date exitTime;
    private double fee;

    public Ticket(String ticketId, Vehicle vehicle, ParkingSpot spot) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = new Date(); // Set entry time to current time
    }

    public void markExit() {
        this.exitTime = new Date(); // Set exit time to current time
        calculateFee();
    }

    private void calculateFee() {
        // Fee calculation logic, possibly using a strategy pattern
        long duration = exitTime.getTime() - entryTime.getTime(); // Duration in milliseconds
        this.fee = duration * 0.05; // Example fee calculation
    }

    public double getFee() {
        return fee;
    }

    // Other ticket-related methods
}