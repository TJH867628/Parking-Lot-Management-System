package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDAO {

    // Insert a payment record
    public void insertPayment(Connection conn, int ticketId, double amount, String method) throws SQLException {
        String sql = "INSERT INTO payment (ticket_id, amount, method, payment_time) VALUES (?, ?, ?, NOW())";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ticketId);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, method);
            pstmt.executeUpdate();
        }
    }

    // Total revenue
    public double getTotalRevenue(Connection conn) throws SQLException {
        String sql = "SELECT SUM(amount) AS total FROM payment";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        }
        return 0.0;
    }

    // Daily revenue (today)
    public double getDailyRevenue(Connection conn) throws SQLException {
        String sql = "SELECT SUM(amount) AS total FROM payment WHERE DATE(payment_time) = CURDATE()";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        }
        return 0.0;
    }

    // Weekly revenue (current week)
    public double getWeeklyRevenue(Connection conn) throws SQLException {
        String sql = "SELECT SUM(amount) AS total FROM payment WHERE YEARWEEK(payment_time) = YEARWEEK(CURDATE())";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        }
        return 0.0;
    }

    // Monthly revenue (current month)
    public double getMonthlyRevenue(Connection conn) throws SQLException {
        String sql = "SELECT SUM(amount) AS total FROM payment WHERE YEAR(payment_time) = YEAR(CURDATE()) AND MONTH(payment_time) = MONTH(CURDATE())";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        }
        return 0.0;
    }
}
