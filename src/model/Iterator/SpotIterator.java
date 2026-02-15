package model.Iterator;

import java.util.*;
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
        if(!hasNext()) {
            throw new NoSuchElementException("No more parking spots available.");
        }
        return spots.get(spotIndex++);
    }
}