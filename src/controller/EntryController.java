package controller;

import dao.TicketDAO;
import dao.VehicleDAO;
import model.EntryResult;
import model.EntrySpot;
import model.Ticket;
import model.VehicleType;
import util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EntryController {
    private VehicleDAO vehicleDAO = new VehicleDAO();
    private TicketDAO ticketDAO = new TicketDAO();

    public List<VehicleType> getVehicleTypes() {
        return vehicleDAO.getAllVehicleTypes();
    }

    public List<EntrySpot> getAvailableSpots(int vehicleTypeId) {
        return ticketDAO.getAvailableSpotsForVehicle(vehicleTypeId);
    }

    public EntryResult registerEntry(String licensePlate, int vehicleTypeId, boolean hasHandicappedCard, int selectedSpotId) {
        String normalizedPlate = normalizePlate(licensePlate);
        if (normalizedPlate == null) {
            return EntryResult.failure("Please enter a valid license plate number.");
        }

        if (vehicleTypeId <= 0) {
            return EntryResult.failure("Please select a valid vehicle type.");
        }

        if (selectedSpotId <= 0) {
            return EntryResult.failure("Please select a parking spot.");
        }

        if (vehicleDAO.hasActiveVehicle(normalizedPlate)) {
            return EntryResult.failure("This vehicle is already parked in the system.");
        }

        EntrySpot selectedSpot = ticketDAO.getSpotById(selectedSpotId);
        if (selectedSpot == null) {
            return EntryResult.failure("Selected spot does not exist.");
        }

        Connection conn = null;
        boolean previousAutoCommit = true;
        try {
            conn = DBConnectionUtil.getConnection();
            previousAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            if (!ticketDAO.isSpotCompatible(conn, selectedSpotId, vehicleTypeId)) {
                conn.rollback();
                return EntryResult.failure("Selected spot is not compatible with this vehicle type.");
            }

            // Re-check and lock by update so two users cannot occupy the same spot at the same time.
            if (!ticketDAO.occupySpot(conn, selectedSpotId, normalizedPlate)) {
                conn.rollback();
                return EntryResult.failure("Selected spot is no longer available.");
            }

            Timestamp entryTime = Timestamp.valueOf(LocalDateTime.now());
            int vehicleId = vehicleDAO.insertVehicle(conn, normalizedPlate, vehicleTypeId, hasHandicappedCard, entryTime);

            String ticketCode = generateTicketCode(normalizedPlate, entryTime.toLocalDateTime());
            int ticketId = ticketDAO.insertTicket(conn, ticketCode, vehicleId, selectedSpotId, entryTime);

            conn.commit();

            Ticket ticket = new Ticket(
                    ticketId,
                    ticketCode,
                    vehicleId,
                    selectedSpotId,
                    entryTime,
                    null,
                    "active",
                    selectedSpot.getSpotCode());

            return EntryResult.success("Vehicle entry recorded and ticket generated.", ticket);
        } catch (SQLException e) {
            rollbackQuietly(conn);
            return EntryResult.failure("Unable to complete vehicle entry: " + e.getMessage());
        } finally {
            resetAutoCommit(conn, previousAutoCommit);
        }
    }

    private String normalizePlate(String licensePlate) {
        if (licensePlate == null) {
            return null;
        }

        String cleaned = licensePlate.trim().toUpperCase().replaceAll("\\s+", "");
        return cleaned.isEmpty() ? null : cleaned;
    }

    private String generateTicketCode(String licensePlate, LocalDateTime entryTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return "T-" + licensePlate + "-" + entryTime.format(formatter);
    }

    private void rollbackQuietly(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            conn.rollback();
        } catch (SQLException ignored) {
        }
    }

    private void resetAutoCommit(Connection conn, boolean previousAutoCommit) {
        if (conn == null) {
            return;
        }
        try {
            conn.setAutoCommit(previousAutoCommit);
        } catch (SQLException ignored) {
        }
    }
}
