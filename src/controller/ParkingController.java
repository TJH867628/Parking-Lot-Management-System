package controller;

import model.ParkingLot;
import model.ParkingFloor;
import model.ParkingSpot;
import model.Iterator.FloorIterator;
import model.Iterator.SpotIterator;
import dao.ParkingLotDAO;

import java.util.*;


public class ParkingController {
    private ParkingLot parkingLot;

    public ParkingController() {
        loadParking();
    }

    private void loadParking() {
        ParkingLotDAO dao = new ParkingLotDAO();
        this.parkingLot = dao.loadParkingLot();
    }

    public FloorIterator getFloorIterator() {
        return parkingLot.getFloorIterator();
    }

    public SpotIterator getSpotIteratorByFloor(int floor_id) {
        ParkingFloor floor = parkingLot.getFloorById(floor_id);
        return floor.getSpots();
    }
}
