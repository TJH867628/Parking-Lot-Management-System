package controller;

import dao.TicketDAO;
import dao.ParkingLotDAO;
import dao.PaymentDAO;
import dao.FineDAO;   // ✅ add FineDAO import
import model.ParkingLot;
import model.ParkingFloor;
import model.ParkingSpot;
import model.Ticket;
import model.Iterator.FloorIterator;
import model.Iterator.SpotIterator;
import util.DBConnectionUtil;

import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;

public class ParkingController {
    private TicketDAO ticketDAO;
    private ParkingLotDAO parkingLotDAO;
    private ParkingLot parkingLot;

    private PaymentDAO paymentDAO = new PaymentDAO();
    private FineDAO fineDAO = new FineDAO();   // ✅ add FineDAO instance

    public ParkingController() {
        this.ticketDAO = new TicketDAO();
        this.parkingLotDAO = new ParkingLotDAO();
        this.parkingLot = parkingLotDAO.loadParkingLot(); // load all floors & spots at startup
    }

    // ---------------- Parked Vehicles ----------------
    public List<String[]> getParkedVehiclesTable() {
        return ticketDAO.getParkedVehiclesWithEntryTime();
    }

    // ---------------- Occupancy ----------------
    public int getTotalSpots() {
        return parkingLot.getTotalSpots();
    }

    public int getOccupiedSpots() {
        return parkingLot.getOccupiedSpots();
    }

    // ---------------- Revenue ----------------
    public double getTotalRevenue() {
        try (Connection conn = DBConnectionUtil.getConnection()) {
            return paymentDAO.getTotalRevenue(conn);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public double getDailyRevenue() {
        try (Connection conn = DBConnectionUtil.getConnection()) {
            return paymentDAO.getDailyRevenue(conn);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public double getWeeklyRevenue() {
        try (Connection conn = DBConnectionUtil.getConnection()) {
            return paymentDAO.getWeeklyRevenue(conn);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public double getMonthlyRevenue() {
        try (Connection conn = DBConnectionUtil.getConnection()) {
            return paymentDAO.getMonthlyRevenue(conn);
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    // ---------------- Floor & Spot Iterators ----------------
    public FloorIterator getFloorIterator() {
        return new FloorIterator(parkingLot.getFloors());
    }

    public SpotIterator getSpotIteratorByFloor(int floorId) {
        ParkingFloor floor = parkingLot.getFloorById(floorId);
        if (floor != null) {
            return new SpotIterator(floor.getSpots());
        }
        return new SpotIterator(List.of()); // empty iterator if floor not found
    }

    // ---------------- Unpaid Fines ----------------
    public List<String[]> getUnpaidFinesTable() {
        try (Connection conn = DBConnectionUtil.getConnection()) {
            return fineDAO.getUnpaidFines(conn);   // ✅ use FineDAO instead of TicketDAO
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Optional: auto-generate fines if needed
    public void checkAndGenerateFines() {
        // You can implement logic here to scan tickets and insert fines if overdue
        // For now, left empty as placeholder
    }
}
