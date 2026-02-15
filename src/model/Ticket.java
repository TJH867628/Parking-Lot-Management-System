package model;

import java.sql.Timestamp;

public class Ticket {
    private int id;
    private String ticketCode;
    private int vehicleId;
    private int spotId;
    private Timestamp entryTime;
    private Timestamp exitTime;
    private String status;
    private String spotCode;

    // NEW: licensePlate field
    private String licensePlate;

    // Existing constructor (unchanged)
    public Ticket(int id, String ticketCode, int vehicleId, int spotId,
                  Timestamp entryTime, Timestamp exitTime, String status, String spotCode) {
        this.id = id;
        this.ticketCode = ticketCode;
        this.vehicleId = vehicleId;
        this.spotId = spotId;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.status = status;
        this.spotCode = spotCode;
    }

    // Getters (existing)
    public int getId() {
        return id;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public int getSpotId() {
        return spotId;
    }

    public Timestamp getEntryTime() {
        return entryTime;
    }

    public Timestamp getExitTime() {
        return exitTime;
    }

    public String getStatus() {
        return status;
    }

    public String getSpotCode() {
        return spotCode;
    }

    // NEW: licensePlate getter/setter
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
