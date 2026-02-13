package model.Iterator;

import java.util.List;
import model.ParkingSpot;

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