package model;

public class EntrySpot {
    private int spotId;
    private int floorId;
    private int rowNumber;
    private int spotNumber;
    private String spotType;
    private double hourlyRate;

    public EntrySpot(int spotId, int floorId, int rowNumber, int spotNumber, String spotType, double hourlyRate) {
        this.spotId = spotId;
        this.floorId = floorId;
        this.rowNumber = rowNumber;
        this.spotNumber = spotNumber;
        this.spotType = spotType;
        this.hourlyRate = hourlyRate;
    }

    public int getSpotId() {
        return spotId;
    }

    public int getFloorId() {
        return floorId;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public String getSpotType() {
        return spotType;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public String getSpotCode() {
        return "F" + floorId + "-R" + rowNumber + "-S" + spotNumber;
    }

    @Override
    public String toString() {
        return getSpotCode() + " | " + spotType + " | RM " + hourlyRate + "/hr";
    }
}
