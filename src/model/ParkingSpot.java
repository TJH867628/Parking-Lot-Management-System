package model;

public class ParkingSpot {
    private int id;
    private String spotCode;
    private String type;
    private String status;
    private String currentVehicle;
    private double ratePerHour;
    private int floor_id;
    private int row_number;
    private int spot_number;

    public ParkingSpot(int id, int floor_id,int row_number ,int spot_number, String type, String status, String currentVehicle,Double hourlyRate) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.currentVehicle = currentVehicle;
        this.floor_id = floor_id;
        this.row_number = row_number;
        this.spot_number = spot_number;
        this.ratePerHour = hourlyRate;
    }

    public boolean isAvailable() {
        return status.equals("available") || status.equals("AVAILABLE") || status.equals("Available");
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getFloorId() {
        return floor_id;
    }

    public int getRowNumber() {
        return row_number;
    }

    public int getSpotNumber() {
        return spot_number;
    }

    public String getSpotCode() {
        return "F" + floor_id + "-R" + row_number + "-S" + spot_number;
    }

    public double getRatePerHour() {
        return ratePerHour;
    }

    public String getCurrentVehicle() {
        return currentVehicle;
    }
    
}
