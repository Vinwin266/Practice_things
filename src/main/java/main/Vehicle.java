package main;

public abstract class Vehicle {
    protected String licensePlate;
    protected String size;

    public Vehicle(String licensePlate, String size) {
        if (licensePlate == null) {
            throw new IllegalArgumentException("License plate cannot be null");
        }
        this.licensePlate = licensePlate;
        this.size = size;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getSize() {
        return size;
    }
}