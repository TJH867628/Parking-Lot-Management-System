package model;

import java.util.ArrayList;
import java.util.List;

import model.Iterator.SpotIterator;

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

    public SpotIterator getSpots() {
        return new SpotIterator(spots);
    }

    public int getTotalSpots() {
        return spots.size();
    }

    public int getOccupiedSpots() {
        int occupied = 0;
        for (ParkingSpot spot : spots) {
            if (!spot.isAvailable()) {
                occupied++;
            }
        }
        return occupied;
    }
}
