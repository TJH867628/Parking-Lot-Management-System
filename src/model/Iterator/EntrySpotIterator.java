package model.Iterator;

import java.util.*;
import model.EntrySpot;

public class EntrySpotIterator implements ParkingIterator<EntrySpot> {

    private List<EntrySpot> spots;
    private int index = 0;

    public EntrySpotIterator(List<EntrySpot> spots) {
        this.spots = spots;
    }

    @Override
    public boolean hasNext() {
        return index < spots.size();
    }

    @Override
    public EntrySpot next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more entry spots available.");
        }
        return spots.get(index++);
    }
}
