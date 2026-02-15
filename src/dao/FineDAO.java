package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FineDAO {

    // Insert a new fine (always starts as unpaid)
    public void insertFine(Connection conn, String licensePlate, double amount, String reason) throws SQLException {
        String sql = "INSERT INTO fine (license_plate, amount, reason, status) VALUES (?, ?, ?, 'unpaid')";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, licensePlate);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, reason);
            pstmt.executeUpdate();
        }
    }

    // ✅ Add this: check if there is already an unpaid fine for this plate
    public boolean hasUnpaidFine(Connection conn, String plate) throws SQLException {
        String sql = "SELECT COUNT(*) AS cnt FROM fine WHERE license_plate = ? AND status = 'unpaid'";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, plate);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("cnt") > 0;
            }
        }
        return false;
    }

    // Mark fines as paid for a given license plate
    public void markFinesPaid(Connection conn, String licensePlate) throws SQLException {
        String sql = "UPDATE fine SET status = 'paid' WHERE license_plate = ? AND status = 'unpaid'";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, licensePlate);
            pstmt.executeUpdate();
        }
    }
    

    // Get all unpaid fines (for dashboard)
    public List<String[]> getUnpaidFines(Connection conn) throws SQLException {
        List<String[]> fines = new ArrayList<>();
        String sql = "SELECT license_plate, amount, status, reason FROM fine WHERE status = 'unpaid'";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                fines.add(new String[]{
                    rs.getString("license_plate"),
                    String.format("%.2f", rs.getDouble("amount")),
                    rs.getString("status"),
                    rs.getString("reason")
                });
            }
        }
        return fines;
    }

    // Get unpaid fines for a specific plate (for ExitController/payment dialog)
    public List<String[]> getUnpaidFinesByPlate(Connection conn, String licensePlate) throws SQLException {
        List<String[]> fines = new ArrayList<>();
        String sql = "SELECT license_plate, amount, status, reason FROM fine WHERE status = 'unpaid' AND license_plate = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, licensePlate);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                fines.add(new String[]{
                    rs.getString("license_plate"),
                    String.format("%.2f", rs.getDouble("amount")),
                    rs.getString("status"),
                    rs.getString("reason")
                });
            }
        }
        return fines;
    }
}
