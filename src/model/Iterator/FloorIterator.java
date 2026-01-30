package model.Iterator;

import model.ParkingFloor;
import java.util.*;

public class FloorIterator implements ParkingIterator<ParkingFloor> {
    private List<ParkingFloor> floors;
    private int index = 0;

    public FloorIterator(List<ParkingFloor> floors) {
        this.floors = floors;
    }

    @Override
    public boolean hasNext() {
        return index < floors.size();
    }

    @Override
    public ParkingFloor next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more floors available.");  
        }

        return floors.get(index++);
    }

}