package model;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private List<ParkingFloor> floors = new ArrayList<>();

    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    public List<ParkingFloor> getFloors() {
        return floors;
    }

    public ParkingFloor getFloorById(int floorId) {
        for (ParkingFloor floor : floors) {
            if (floor.getFloorId() == floorId) {
                return floor;
            }
        }
        return null;
    }

    public int getTotalSpots() {
        int total = 0;
        for (ParkingFloor floor : floors) {
            total += floor.getSpots().size();
        }
        return total;
    }

    public int getOccupiedSpots() {
        int occupied = 0;
        for (ParkingFloor floor : floors) {
            for (ParkingSpot spot : floor.getSpots()) {
                if (!spot.isAvailable()) {
                    occupied++;
                }
            }
        }
        return occupied;
    }
}
