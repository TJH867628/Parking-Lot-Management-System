package model;
import java.util.*;
import model.Iterator.SpotIterator;

public class ParkingFloor {
    private int floor_id,floor_number;
    private List<ParkingSpot> spots = new ArrayList<>();

    public ParkingFloor(int floor_id,int floor_number) {
        this.floor_id = floor_id;
        this.floor_number = floor_number;
    }

    public int getFloorNumber() {
        return floor_number;
    }

    public int getFloorId() {
        return floor_id;
    }

    public void addSpot(ParkingSpot spot) {
        spots.add(spot);
    }

    public SpotIterator getSpots() {
        return new SpotIterator(spots);
    }
}