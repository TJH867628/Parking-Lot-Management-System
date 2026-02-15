package model;

public class SpotRule {
    private int id;
    private String vehicleType;
    private int spotTypeId;

    public SpotRule(int id, String vehicleType, int spotTypeId) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.spotTypeId = spotTypeId;
    }

    public int getId() { return id; }
    public String getVehicleType() { return vehicleType; }
    public int getSpotTypeId() { return spotTypeId; }
}
