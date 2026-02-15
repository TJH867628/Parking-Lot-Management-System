package model.Iterator;

import java.util.*;

import model.ParkedVehicle;
import model.ParkedVehicle;

public class ParkedVehicleIterator implements ParkingIterator<ParkedVehicle> {

    private List<ParkedVehicle> vehicles;
    private int index = 0;

    public ParkedVehicleIterator(List<ParkedVehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public boolean hasNext() {
        return index < vehicles.size();
    }

    @Override
    public ParkedVehicle next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more parked vehicles available.");
        }
        return vehicles.get(index++);
    }
}
