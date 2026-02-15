package model;

import java.util.ArrayList;
import java.util.List;

public class ParkingFloor {
    private int floorId;
    private int floorNumber;
    private List<ParkingSpot> spots = new ArrayList<>();

    public ParkingFloor(int floorId, int floorNumber) {
        this.floorId = floorId;
        this.floorNumber = floorNumber;
    }

    public int getFloorId() {
        return floorId;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void addSpot(ParkingSpot spot) {
        spots.add(spot);
    }

    public List<ParkingSpot> getSpots() {
        return spots;
    }
}
