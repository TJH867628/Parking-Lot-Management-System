package controller;

import dao.TicketDAO;
import dao.ParkingLotDAO;
import dao.PaymentDAO;
import dao.ReportDAO;
import dao.FineDAO;
import model.ParkingLot;
import model.ParkingFloor;
import model.Iterator.FloorIterator;
import model.Iterator.SpotIterator;
import model.Iterator.SpotRuleIterator;
import model.Iterator.ParkedVehicleIterator;
import model.Iterator.FineIterator;
import util.DBConnectionUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ParkingController {
    private TicketDAO ticketDAO;
    private ParkingLotDAO parkingLotDAO;
    private ParkingLot parkingLot;

    private PaymentDAO paymentDAO = new PaymentDAO();
    private FineDAO fineDAO = new FineDAO();
    private ReportDAO reportDAO = new ReportDAO();

    public ParkingController() {
        this.ticketDAO = new TicketDAO();
        this.parkingLotDAO = new ParkingLotDAO();
        this.parkingLot = parkingLotDAO.loadParkingLot();
    }

    public ParkedVehicleIterator getParkedVehiclesTable() {
        return ticketDAO.getParkedVehiclesWithEntryTime();
    }

    public int getTotalSpots() {
        return parkingLot.getTotalSpots();
    }

    public int getOccupiedSpots() {
        return parkingLot.getOccupiedSpots();
    }

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

    public FloorIterator getFloorIterator() {
        return parkingLot.getFloors();
    }

    public SpotIterator getSpotIteratorByFloor(int floorId) {
        ParkingFloor floor = parkingLot.getFloorById(floorId);
        if (floor != null) {
            return floor.getSpots();
        }
        return new SpotIterator(new ArrayList<>());
    }

    public FineIterator getUnpaidFinesTable() {
        try (Connection conn = DBConnectionUtil.getConnection()) {
            return fineDAO.getUnpaidFines(conn);
        } catch (Exception e) {
            e.printStackTrace();
            return new FineIterator(new ArrayList<>());
        }
    }

    public boolean isSpotEligibleForVehicle(int spotTypeId, int vehicleTypeId) {
        SpotRuleIterator rules = parkingLotDAO.getSpotRulesByVehicleType(vehicleTypeId);
        while (rules.hasNext()) {
            if (rules.next().getSpotTypeId() == spotTypeId) {
                return true;
            }
        }
        return false;
    }

    public void checkAndGenerateFines() {
        try (Connection conn = DBConnectionUtil.getConnection()) {
            fineDAO.generateFinesForOverstayedVehicles(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getTotalUnpaidFineAmount() {
        try (Connection conn = DBConnectionUtil.getConnection()) {
            return fineDAO.getTotalUnpaidFineAmount();
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public List<String[]> getOccupancyBySpotType() {
        return reportDAO.getOccupancyBySpotType();
    }
}
