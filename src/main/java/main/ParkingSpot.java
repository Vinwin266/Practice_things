package main;
import main.Vehicle;
public class ParkingSpot {
    private String spotId;
    private String size;
    private boolean isOccupied;
    private Vehicle vehicle;

    public ParkingSpot(String spotId, String size) {
        this.spotId = spotId;
        this.size = size;
        this.isOccupied = false;
        this.vehicle = null;
    }

    public void assignVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.isOccupied = true;
    }

    public void removeVehicle() {
        this.vehicle = null;
        this.isOccupied = false;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public String getSize() {
        return size;
    }

    // Other methods related to spot management
}