package model.Iterator;

import java.util.*;

public class FineIterator implements ParkingIterator<String[]> {

    private List<String[]> fines;
    private int index = 0;

    public FineIterator(List<String[]> fines) {
        this.fines = fines;
    }

    @Override
    public boolean hasNext() {
        return index < fines.size();
    }

    @Override
    public String[] next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more fines available.");
        }
        return fines.get(index++);
    }
}
