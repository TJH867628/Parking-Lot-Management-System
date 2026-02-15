package dao;

import model.EntrySpot;
import model.Ticket;
import util.DBConnectionUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {

    public List<EntrySpot> getAvailableSpotsForVehicle(int vehicleTypeId) {
        List<EntrySpot> spots = new ArrayList<>();

        String sql = "SELECT ps.id, " +
                "ps.floor_id, " +
                "ps.row_number, " +
                "ps.spot_number, " +
                "pst.name AS spot_type, " +
                "pst.hourly_rate " +
                "FROM parking_spot ps " +
                "JOIN parking_spot_type pst ON pst.id = ps.type_id " +
                "JOIN vehicle_spot_rule vsr ON vsr.spot_type = ps.type_id " +
                "WHERE LOWER(ps.status) = 'available' " +
                "AND vsr.vehicle_type = ? " +
                "ORDER BY ps.floor_id, ps.row_number, ps.spot_number";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, String.valueOf(vehicleTypeId));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                spots.add(mapSpot(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return spots;
    }

    public EntrySpot getSpotById(int spotId) {
        String sql = "SELECT ps.id, " +
                "ps.floor_id, " +
                "ps.row_number, " +
                "ps.spot_number, " +
                "pst.name AS spot_type, " +
                "pst.hourly_rate " +
                "FROM parking_spot ps " +
                "JOIN parking_spot_type pst ON pst.id = ps.type_id " +
                "WHERE ps.id = ? " +
                "LIMIT 1";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, spotId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapSpot(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean isSpotCompatible(Connection conn, int spotId, int vehicleTypeId) throws SQLException {
        String sql = "SELECT ps.id " +
                "FROM parking_spot ps " +
                "JOIN vehicle_spot_rule vsr ON vsr.spot_type = ps.type_id " +
                "WHERE ps.id = ? " +
                "AND vsr.vehicle_type = ? " +
                "LIMIT 1";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, spotId);
            pstmt.setString(2, String.valueOf(vehicleTypeId));
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    public boolean occupySpot(Connection conn, int spotId, String licensePlate) throws SQLException {
        String sql = "UPDATE parking_spot " +
                "SET status = 'parked', " +
                "current_vehicle = ? " +
                "WHERE id = ? " +
                "AND LOWER(status) = 'available'";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, licensePlate);
            pstmt.setInt(2, spotId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows == 1;
        }
    }

    public int insertTicket(Connection conn, String ticketCode, int vehicleId, int spotId, Timestamp entryTime)
            throws SQLException {
        String sql = "INSERT INTO ticket (ticket_code, vehicle_id, spot_id, entry_time, exit_time, status) " +
                "VALUES (?, ?, ?, ?, NULL, 'active')";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, ticketCode);
            pstmt.setInt(2, vehicleId);
            pstmt.setInt(3, spotId);
            pstmt.setTimestamp(4, entryTime);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating ticket failed, no row inserted.");
            }

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

            throw new SQLException("Creating ticket failed, no ID returned.");
        }
    }

    private EntrySpot mapSpot(ResultSet rs) throws SQLException {
        return new EntrySpot(
                rs.getInt("id"),
                rs.getInt("floor_id"),
                rs.getInt("row_number"),
                rs.getInt("spot_number"),
                rs.getString("spot_type"),
                rs.getDouble("hourly_rate"));
    }

    public List<Ticket> getActiveTickets() {
    List<Ticket> tickets = new ArrayList<>();

    String sql = "SELECT t.id, t.ticket_code, t.vehicle_id, t.spot_id, " +
                 "t.entry_time, t.exit_time, t.status, " +
                 "CONCAT('F', ps.floor_id, '-R', ps.row_number, '-S', ps.spot_number) AS spot_code, " +
                 "v.license_plate " +
                 "FROM ticket t " +
                 "JOIN parking_spot ps ON t.spot_id = ps.id " +
                 "JOIN vehicle v ON t.vehicle_id = v.id " +
                 "WHERE t.status = 'active'";

    try (Connection conn = DBConnectionUtil.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            Ticket ticket = new Ticket(
                rs.getInt("id"),
                rs.getString("ticket_code"),
                rs.getInt("vehicle_id"),
                rs.getInt("spot_id"),
                rs.getTimestamp("entry_time"),
                rs.getTimestamp("exit_time"),
                rs.getString("status"),
                rs.getString("spot_code")
            );

            // NEW: set license plate separately
            ticket.setLicensePlate(rs.getString("license_plate"));

            tickets.add(ticket);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return tickets;
}



    // ---------------- NEW METHOD ----------------
    public List<String[]> getParkedVehiclesWithEntryTime() {
        List<String[]> vehicles = new ArrayList<>();

        String sql = "SELECT ps.floor_id, ps.row_number, ps.spot_number, ps.current_vehicle, t.entry_time " +
                     "FROM parking_spot ps " +
                     "JOIN ticket t ON ps.id = t.spot_id " +
                     "WHERE LOWER(ps.status) = 'parked' AND t.status = 'active'";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String floor = String.valueOf(rs.getInt("floor_id"));
                String spotCode = "F" + rs.getInt("floor_id") +
                                  "-R" + rs.getInt("row_number") +
                                  "-S" + rs.getInt("spot_number");
                String vehiclePlate = rs.getString("current_vehicle");

                Timestamp entryTime = rs.getTimestamp("entry_time");
                String entryTimeStr = (entryTime != null)
                        ? new SimpleDateFormat("dd MMM yyyy, HH:mm").format(entryTime)
                        : "-";

                vehicles.add(new String[]{floor, spotCode, vehiclePlate, entryTimeStr});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }

    public void insertFine(String plate, double amount, String status, String reason) {
    String checkSql = "SELECT COUNT(*) FROM fine WHERE license_plate = ? AND LOWER(status) = 'unpaid' AND reason = ?";
    String insertSql = "INSERT INTO fine (license_plate, amount, status, reason) VALUES (?, ?, ?, ?)";

    try (Connection conn = DBConnectionUtil.getConnection();
         PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

        // Check if an unpaid fine already exists for this plate + reason
        checkStmt.setString(1, plate);
        checkStmt.setString(2, reason);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next() && rs.getInt(1) > 0) {
            // Already fined → skip insert
            return;
        }

        // Otherwise, insert new fine
        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            insertStmt.setString(1, plate);
            insertStmt.setDouble(2, amount);
            insertStmt.setString(3, status);
            insertStmt.setString(4, reason);
            insertStmt.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    // ---------------- NEW METHOD ----------------
    public List<String[]> getUnpaidFines() {
        List<String[]> fines = new ArrayList<>();

        String sql = "SELECT license_plate, amount, status, reason " +
                    "FROM fine " +
                    "WHERE LOWER(status) = 'unpaid'";

        try (Connection conn = DBConnectionUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String plate = rs.getString("license_plate");
                String amount = String.format("%.2f", rs.getDouble("amount"));
                String status = rs.getString("status");
                String reason = rs.getString("reason");

                fines.add(new String[]{plate, amount, status, reason});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fines;
    }
}