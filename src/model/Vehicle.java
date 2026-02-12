package model;

import java.sql.Timestamp;

public class Vehicle {
    private int id;
    private String licensePlate;
    private int vehicleTypeId;
    private boolean hasHandicappedCard;
    private Timestamp entryTime;
    private Timestamp exitTime;

    public Vehicle(int id, String licensePlate, int vehicleTypeId, boolean hasHandicappedCard, Timestamp entryTime, Timestamp exitTime) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.vehicleTypeId = vehicleTypeId;
        this.hasHandicappedCard = hasHandicappedCard;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    public int getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public int getVehicleTypeId() {
        return vehicleTypeId;
    }

    public boolean hasHandicappedCard() {
        return hasHandicappedCard;
    }

    public Timestamp getEntryTime() {
        return entryTime;
    }

    public Timestamp getExitTime() {
        return exitTime;
    }
}
