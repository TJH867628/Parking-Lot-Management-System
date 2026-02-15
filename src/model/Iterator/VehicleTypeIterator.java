package model.Iterator;

import java.util.*;
import model.VehicleType;

public class VehicleTypeIterator implements ParkingIterator<VehicleType> {

    private List<VehicleType> vehicleTypes;
    private int index = 0;

    public VehicleTypeIterator(List<VehicleType> vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }

    @Override
    public boolean hasNext() {
        return index < vehicleTypes.size();
    }

    @Override
    public VehicleType next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more vehicle types available.");
        }
        return vehicleTypes.get(index++);
    }
}
