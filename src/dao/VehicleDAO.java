package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.VehicleType;
import model.Iterator.VehicleTypeIterator;
import util.DBConnectionUtil;

public class VehicleDAO {

    public VehicleTypeIterator getAllVehicleTypes() {
        List<VehicleType> vehicleTypes = new ArrayList<>();
        String sql = "SELECT id, name FROM vehicle_type ORDER BY id";

        try (Connection conn = DBConnectionUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                vehicleTypes.add(
                        new VehicleType(
                                rs.getInt("id"),
                                rs.getString("name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new VehicleTypeIterator(vehicleTypes);
    }

    public boolean hasActiveVehicle(String licensePlate) {
        String sql = "SELECT id " +
                "FROM vehicle " +
                "WHERE license_plate = ? " +
                "AND exit_time IS NULL " +
                "LIMIT 1";

        try (Connection conn = DBConnectionUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, licensePlate);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int insertVehicle(Connection conn, String licensePlate, int vehicleTypeId, boolean hasHandicappedCard,
            Timestamp entryTime) throws SQLException {
        String sql = "INSERT INTO vehicle (license_plate, vehicle_type_id, has_handicapped_card, entry_time, exit_time) "
                +
                "VALUES (?, ?, ?, ?, NULL)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, licensePlate);
            pstmt.setInt(2, vehicleTypeId);
            pstmt.setBoolean(3, hasHandicappedCard);
            pstmt.setTimestamp(4, entryTime);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating vehicle failed, no row inserted.");
            }

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

            throw new SQLException("Creating vehicle failed, no ID returned.");
        }
    }

    
}
