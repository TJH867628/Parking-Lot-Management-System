package model.Iterator;

public interface ParkingIterator<T> {
    boolean hasNext();
    T next();
}