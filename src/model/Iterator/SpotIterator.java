package model.Iterator;

import model.ParkingFloor;
import model.ParkingSpot;
import java.util.List;

public class SpotIterator implements ParkingIterator<ParkingSpot> {

    private List<ParkingSpot> spots;
    private int floorIndex = 0;
    private int spotIndex = 0;

    public SpotIterator(List<ParkingSpot> spots) {
        this.spots = spots;
    }

    @Override
    public boolean hasNext() {
        return spotIndex < spots.size();
    }

    @Override
    public ParkingSpot next() {
        return spots.get(spotIndex++);
    }
}