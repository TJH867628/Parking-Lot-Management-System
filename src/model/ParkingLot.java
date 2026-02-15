package model;

import java.util.ArrayList;
import java.util.List;

import model.Iterator.FloorIterator;

public class ParkingLot {
    private List<ParkingFloor> floors = new ArrayList<>();

    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    public FloorIterator getFloors() {
        return new FloorIterator(floors);
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
            total += floor.getTotalSpots();
        }
        return total;
    }

    public int getOccupiedSpots() {
        int occupied = 0;
        for (ParkingFloor floor : floors) {
            occupied += floor.getOccupiedSpots();
        }
        return occupied;
    }
}
