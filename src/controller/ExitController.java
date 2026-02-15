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

            if (!rs.next()) return "No active parking found.";

            int spotId = rs.getInt("spot_id");
            Timestamp entryTime = rs.getTimestamp("entry_time");
            double rate = rs.getDouble("hourly_rate");

            Timestamp exitTime = Timestamp.valueOf(LocalDateTime.now());

            long minutes = Duration.between(entryTime.toLocalDateTime(), exitTime.toLocalDateTime()).toMinutes();
            long hours = (long) Math.ceil(minutes / 60.0);

            boolean isHandicapped = checkIfHandicappedVehicle(conn, plate);
            boolean isHandicappedSpot = checkIfHandicappedSpot(conn, spotId);

            double parkingFee = (isHandicapped && isHandicappedSpot)
                    ? hours * 2.0
                    : hours * rate;

            double unpaid = fineDAO.getUnpaidFines(conn, plate);
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
            if (!rs.next()) return -1;

            int ticketId = rs.getInt("id");
            int spotId = rs.getInt("spot_id");

            Timestamp entryTime = rs.getTimestamp("entry_time");
            Timestamp exitTime = Timestamp.valueOf(LocalDateTime.now());

            long minutes = Duration.between(entryTime.toLocalDateTime(), exitTime.toLocalDateTime()).toMinutes();
            long hours = (long) Math.ceil(minutes / 60.0);

            double rate = rs.getDouble("hourly_rate");

            boolean isHandicapped = checkIfHandicappedVehicle(conn, plate);
            boolean isHandicappedSpot = checkIfHandicappedSpot(conn, spotId);

            double parkingFee = (isHandicapped && isHandicappedSpot)
                    ? hours * 2.0
                    : hours * rate;

            double fineAmount = calculateFine(hours);

            if (isReservedSpot(conn, spotId) && !isVIPVehicle(conn, plate)) {
                fineAmount += 100;
            }

            if (fineAmount > 0) {
                fineDAO.insertFine(conn, plate, fineAmount);
            }

            double unpaid = fineDAO.getUnpaidFines(conn, plate);
            double total = parkingFee + unpaid;

            paymentDAO.insertPayment(conn, ticketId, total, method);

            PreparedStatement pt = conn.prepareStatement(
                    "UPDATE ticket SET exit_time = ?, status = 'paid' WHERE id = ?");
            pt.setTimestamp(1, exitTime);
            pt.setInt(2, ticketId);
            pt.executeUpdate();

            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE parking_spot SET status = 'available', current_vehicle = NULL WHERE id = ?");
            ps.setInt(1, spotId);
            ps.executeUpdate();

            PreparedStatement pv = conn.prepareStatement(
                    "UPDATE vehicle SET exit_time = ? WHERE license_plate = ? AND exit_time IS NULL ORDER BY id DESC LIMIT 1");
            pv.setTimestamp(1, exitTime);
            pv.setString(2, plate);
            pv.executeUpdate();

            fineDAO.markFinesPaid(conn, plate);

            conn.commit();

            return unpaid;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String buildReceipt(String plate, String method, double finePaid) {
        try (Connection conn = DBConnectionUtil.getConnection()) {

            String sql = "SELECT t.entry_time, t.exit_time, pst.hourly_rate " +
                    "FROM ticket t " +
                    "JOIN parking_spot ps ON t.spot_id = ps.id " +
                    "JOIN parking_spot_type pst ON ps.type_id = pst.id " +
                    "JOIN vehicle v ON t.vehicle_id = v.id " +
                    "WHERE v.license_plate = ? " +
                    "ORDER BY t.id DESC LIMIT 1";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, plate);

            ResultSet rs = pstmt.executeQuery();
            if (!rs.next()) return "No receipt found.";

            Timestamp entryTime = rs.getTimestamp("entry_time");
            Timestamp exitTime = rs.getTimestamp("exit_time");
            double rate = rs.getDouble("hourly_rate");

            long minutes = Duration.between(entryTime.toLocalDateTime(), exitTime.toLocalDateTime()).toMinutes();
            long hours = (long) Math.ceil(minutes / 60.0);

            double parkingFee = hours * rate;
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
                    method
            );

        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating receipt.";
        }
    }

    private double calculateFine(long hours) {
        if (hours <= 24) return 0;

        switch (fineScheme) {
            case "A": return 50;
            case "B": return (hours <= 48) ? 150 : (hours <= 72 ? 300 : 500);
            case "C": return (hours - 24) * 20;
            default: return 50;
        }
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
                "SELECT pst.name FROM parking_spot ps JOIN parking_spot_type pst ON ps.type_id = pst.id WHERE ps.id = ?");
        pstmt.setInt(1, spotId);
        ResultSet rs = pstmt.executeQuery();
        return rs.next() && rs.getString("name").equalsIgnoreCase("Handicapped");
    }

    private boolean isReservedSpot(Connection conn, int spotId) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(
                "SELECT pst.name FROM parking_spot ps JOIN parking_spot_type pst ON ps.type_id = pst.id WHERE ps.id = ?");
        pstmt.setInt(1, spotId);
        ResultSet rs = pstmt.executeQuery();
        return rs.next() && rs.getString("name").equalsIgnoreCase("Reserved");
    }

    private boolean isVIPVehicle(Connection conn, String plate) {
        return false;
    }
}
