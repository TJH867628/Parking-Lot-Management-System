package model;
import java.util.*;
import model.Iterator.*;

public class ParkingLot {
    private List<ParkingFloor> floors = new ArrayList<>();

    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    public FloorIterator getFloorIterator() {
        return new FloorIterator(floors);
    }

    public ParkingFloor getFloorById(int floor_id) {
        for (ParkingFloor floor : floors) {
            if (floor.getFloorId() == floor_id) {
                return floor;
            }
        }
        return null;
    }
}