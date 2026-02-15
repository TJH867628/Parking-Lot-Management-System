package model;

import java.sql.Timestamp;

public class ParkedVehicle {

    private int floorId;
    private int rowNumber;
    private int spotNumber;
    private String licensePlate;
    private Timestamp entryTime;

    public ParkedVehicle(int floorId,int rowNumber,int spotNumber,String licensePlate, Timestamp entryTime) {
        this.floorId = floorId;
        this.rowNumber = rowNumber;
        this.spotNumber = spotNumber;
        this.licensePlate = licensePlate;
        this.entryTime = entryTime;
    }

    public int getFloorId() { return floorId; }
    public int getRowNumber() { return rowNumber; }
    public int getSpotNumber() { return spotNumber; }
    public String getLicensePlate() { return licensePlate; }
    public Timestamp getEntryTime() { return entryTime; }

    public String getSpotCode() {
        return "F" + floorId +
               "-R" + rowNumber +
               "-S" + spotNumber;
    }
}
