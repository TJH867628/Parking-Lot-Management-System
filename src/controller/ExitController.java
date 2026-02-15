package controller;

import dao.FineDAO;
import dao.PaymentDAO;
import util.DBConnectionUtil;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExitController {

    private FineDAO fineDAO = new FineDAO();
    private PaymentDAO paymentDAO = new PaymentDAO();

    private String fineScheme = "A";

    public String calculateExit(String plate) {
        try (Connection conn = DBConnectionUtil.getConnection()) {

            String sql = "SELECT t.id, t.entry_time, t.spot_id, pst.hourly_rate " +
                    "FROM ticket t " +
                    "JOIN parking_spot ps ON t.spot_id = ps.id " +
                    "JOIN parking_spot_type pst ON ps.type_id = pst.id " +
                    "JOIN vehicle v ON t.vehicle_id = v.id " +
                    "WHERE v.license_plate = ? AND t.exit_time IS NULL " +
                    "LIMIT 1";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, plate);

            ResultSet rs = pstmt.executeQuery();

            if (!rs.next())
                return "No active parking found.";

            int spotId = rs.getInt("spot_id");
            Timestamp entryTime = rs.getTimestamp("entry_time");
            double rate = rs.getDouble("hourly_rate");

            Timestamp exitTime = Timestamp.valueOf(LocalDateTime.now());

            long minutes = Duration.between(entryTime.toLocalDateTime(), exitTime.toLocalDateTime()).toMinutes();
            long hours = (long) Math.ceil(minutes / 60.0);

            boolean isHandicapped = checkIfHandicappedVehicle(conn, plate);
            boolean isHandicappedSpot = checkIfHandicappedSpot(conn, spotId);

            double parkingFee = 0;
            if (isHandicapped && isHandicappedSpot) {
                parkingFee = 0;
            } else if (isHandicapped && !isHandicappedSpot) {
                parkingFee = hours * 2.0;
            } else {
                parkingFee = hours * rate;
            }

            double unpaid = getUnpaidFineAmount(conn, plate);
            double total = parkingFee + unpaid;

            return "Hours: " + hours +
                    "\nParking Fee: RM " + parkingFee +
                    "\nUnpaid Fines: RM " + unpaid +
                    "\n----------------------" +
                    "\nTOTAL: RM " + total;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error calculating exit.";
        }
    }

    public double processPayment(String plate, String method) {
        try (Connection conn = DBConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);

            double parkingFee = calculateParkingFee(conn, plate);
            double fineAmount = calculateFine(conn, plate);
            String reason = (fineAmount > 0) ? "Overdue parking" : null;

            if (fineAmount > 0 && reason != null) {
                if (!fineDAO.hasUnpaidFine(conn, plate)) {
                    fineDAO.insertFine(conn, plate, fineAmount, reason);
                }
            }

            double total = parkingFee + fineAmount;

            int ticketId = getTicketId(conn, plate);
            paymentDAO.insertPayment(conn, ticketId, total, method);

            updateTicketExit(conn, ticketId);
            updateSpotAvailability(conn, ticketId);
            updateVehicleExit(conn, plate);

            fineDAO.markFinesPaid(conn, plate);

            conn.commit();

            return fineAmount;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String buildReceipt(String plate, String method, double finePaid) {
        try (Connection conn = DBConnectionUtil.getConnection()) {

            String sql = "SELECT t.entry_time, t.exit_time, t.spot_id, pst.hourly_rate " +
                    "FROM ticket t " +
                    "JOIN parking_spot ps ON t.spot_id = ps.id " +
                    "JOIN parking_spot_type pst ON ps.type_id = pst.id " +
                    "JOIN vehicle v ON t.vehicle_id = v.id " +
                    "WHERE v.license_plate = ? " +
                    "ORDER BY t.id DESC LIMIT 1";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, plate);

            ResultSet rs = pstmt.executeQuery();
            if (!rs.next())
                return "No receipt found.";

            Timestamp entryTime = rs.getTimestamp("entry_time");
            Timestamp exitTime = rs.getTimestamp("exit_time");
            double rate = rs.getDouble("hourly_rate");

            long minutes = Duration.between(entryTime.toLocalDateTime(), exitTime.toLocalDateTime()).toMinutes();
            long hours = (long) Math.ceil(minutes / 60.0);
            boolean isHandicapped = checkIfHandicappedVehicle(conn, plate);
            boolean isHandicappedSpot = checkIfHandicappedSpot(conn, rs.getInt("spot_id"));

            double parkingFee = 0;
            if (isHandicapped && isHandicappedSpot) {
                parkingFee = 0;
            } else if (isHandicapped && !isHandicappedSpot) {
                parkingFee = hours * 2.0;
            } else {
                parkingFee = hours * rate;
            }
            double total = parkingFee + finePaid;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            return String.format(
                    "========= RECEIPT =========\n" +
                            "Plate No   : %s\n" +
                            "Entry Time : %s\n" +
                            "Exit Time  : %s\n" +
                            "Hours      : %d\n\n" +
                            "Parking Fee: RM %.2f\n" +
                            "Fines      : RM %.2f\n" +
                            "--------------------------\n" +
                            "TOTAL      : RM %.2f\n\n" +
                            "Payment    : %s\n" +
                            "==========================",
                    plate,
                    entryTime.toLocalDateTime().format(formatter),
                    exitTime.toLocalDateTime().format(formatter),
                    hours,
                    parkingFee,
                    finePaid,
                    total,
                    method);

        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating receipt.";
        }
    }

    // Fine calculation logic
    private double calculateFine(long hours) {
        if (hours <= 24)
            return 0;

        switch (fineScheme) {
            case "A":
                return 50;
            case "B":
                return (hours <= 48) ? 150 : (hours <= 72 ? 300 : 500);
            case "C":
                return (hours - 24) * 20;
            default:
                return 50;
        }
    }

    // Calculate fine for a plate
    private double calculateFine(Connection conn, String plate) throws SQLException {
        String sql = "SELECT t.entry_time FROM ticket t " +
                "JOIN vehicle v ON t.vehicle_id = v.id " +
                "WHERE v.license_plate = ? AND t.exit_time IS NULL LIMIT 1";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, plate);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next())
                return 0.0;

            Timestamp entryTime = rs.getTimestamp("entry_time");
            Timestamp exitTime = Timestamp.valueOf(LocalDateTime.now());
            long minutes = Duration.between(entryTime.toLocalDateTime(), exitTime.toLocalDateTime()).toMinutes();
            long hours = (long) Math.ceil(minutes / 60.0);

            return calculateFine(hours);
        }
    }

    // Calculate parking fee
    private double calculateParkingFee(Connection conn, String plate) throws SQLException {
        String sql = "SELECT t.entry_time, t.spot_id, pst.hourly_rate " +
                "FROM ticket t " +
                "JOIN parking_spot ps ON t.spot_id = ps.id " +
                "JOIN parking_spot_type pst ON ps.type_id = pst.id " +
                "JOIN vehicle v ON t.vehicle_id = v.id " +
                "WHERE v.license_plate = ? AND t.exit_time IS NULL LIMIT 1";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, plate);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.next())
                return 0.0;

            Timestamp entryTime = rs.getTimestamp("entry_time");
            int spotId = rs.getInt("spot_id");
            double rate = rs.getDouble("hourly_rate");

            Timestamp exitTime = Timestamp.valueOf(LocalDateTime.now());
            long minutes = Duration.between(entryTime.toLocalDateTime(), exitTime.toLocalDateTime()).toMinutes();
            long hours = (long) Math.ceil(minutes / 60.0);

            boolean isHandicapped = checkIfHandicappedVehicle(conn, plate);
            boolean isHandicappedSpot = checkIfHandicappedSpot(conn, spotId);

            if (isHandicapped && isHandicappedSpot) {
                return 0;
            } else if (isHandicapped && !isHandicappedSpot) {
                return hours * 2.0;
            } else {
                return hours * rate;
            }

        }
    }

    private double getUnpaidFineAmount(Connection conn, String plate) throws SQLException {
        String sql = "SELECT SUM(amount) AS total FROM fine WHERE license_plate = ? AND status = 'unpaid'";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, plate);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        }
        return 0.0;
    }

    private boolean checkIfHandicappedVehicle(Connection conn, String plate) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
                "SELECT has_handicapped_card FROM vehicle WHERE license_plate = ? ORDER BY id DESC LIMIT 1");
        pstmt.setString(1, plate);
        ResultSet rs = pstmt.executeQuery();
        return rs.next() && rs.getBoolean("has_handicapped_card");
    }

    private boolean checkIfHandicappedSpot(Connection conn, int spotId) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
                "SELECT pst.name FROM parking_spot ps " +
                        "JOIN parking_spot_type pst ON ps.type_id = pst.id " +
                        "WHERE ps.id = ?");
        pstmt.setInt(1, spotId);
        ResultSet rs = pstmt.executeQuery();
        return rs.next() && rs.getString("name").equalsIgnoreCase("Handicapped");
    }

    private boolean isReservedSpot(Connection conn, int spotId) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
                "SELECT pst.name FROM parking_spot ps " +
                        "JOIN parking_spot_type pst ON ps.type_id = pst.id " +
                        "WHERE ps.id = ?");
        pstmt.setInt(1, spotId);
        ResultSet rs = pstmt.executeQuery();
        return rs.next() && rs.getString("name").equalsIgnoreCase("Reserved");
    }

    private boolean isVIPVehicle(Connection conn, String plate) {
        // Placeholder for VIP logic if needed
        return false;
    }

    // Retrieve the active ticket ID for a vehicle
    private int getTicketId(Connection conn, String plate) throws SQLException {
        String sql = "SELECT t.id " +
                "FROM ticket t " +
                "JOIN vehicle v ON t.vehicle_id = v.id " +
                "WHERE v.license_plate = ? AND t.exit_time IS NULL " +
                "ORDER BY t.id DESC LIMIT 1";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, plate);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        throw new SQLException("No active ticket found for plate: " + plate);
    }

    // Update ticket with exit time
    private void updateTicketExit(Connection conn, int ticketId) throws SQLException {
        String sql = "UPDATE ticket SET exit_time = NOW(), status = 'paid' WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ticketId);
            pstmt.executeUpdate();
        }
    }

    // Free up the parking spot
    private void updateSpotAvailability(Connection conn, int ticketId) throws SQLException {
        String sql = "UPDATE parking_spot ps " +
                "JOIN ticket t ON ps.id = t.spot_id " +
                "SET ps.status = 'available', ps.current_vehicle = NULL " +
                "WHERE t.id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ticketId);
            pstmt.executeUpdate();
        }
    }

    // Mark vehicle as exited
    private void updateVehicleExit(Connection conn, String plate) throws SQLException {
        String sql = "UPDATE vehicle SET has_handicapped_card = has_handicapped_card, exit_time = ? " +
                "WHERE license_plate = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setString(2, plate);
            pstmt.executeUpdate();
        }
    }
}
