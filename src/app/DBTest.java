package app;

import util.DBConnectionUtil;
import dao.ParkingLotDAO;
import model.ParkingLot;
import model.Iterator.FloorIterator;
import model.Iterator.SpotIterator;

public class DBTest {
    public static void main(String[] args) {
        ParkingLotDAO dao = new ParkingLotDAO();
        ParkingLot lot = dao.loadParkingLot();
    }
}