package dao;

import model.EntrySpot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import util.DBConnectionUtil;

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
}
