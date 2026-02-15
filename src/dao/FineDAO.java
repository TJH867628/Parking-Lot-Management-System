package dao;

import java.sql.*;

public class FineDAO {

    public void insertFine(Connection conn, String plate, double amount) throws SQLException {
        String sql = "INSERT INTO fine (license_plate, amount, status, created_at) VALUES (?, ?, 'unpaid', NOW())";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, plate);
        pstmt.setDouble(2, amount);
        pstmt.executeUpdate();
    }

    public double getUnpaidFines(Connection conn, String plate) throws SQLException {
        String sql = "SELECT SUM(amount) AS total FROM fine WHERE license_plate = ? AND status = 'unpaid'";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, plate);

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getDouble("total");
        }
        return 0;
    }

    public void markFinesPaid(Connection conn, String plate) throws SQLException {
        String sql = "UPDATE fine SET status = 'paid' WHERE license_plate = ? AND status = 'unpaid'";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, plate);
        pstmt.executeUpdate();
    }
}
