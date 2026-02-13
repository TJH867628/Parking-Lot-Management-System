package dao;

import java.sql.*;

public class PaymentDAO {

    public void insertPayment(Connection conn, int ticketId, double amount, String method) throws SQLException {
        String sql = "INSERT INTO payment (ticket_id, amount, method, payment_time) VALUES (?, ?, ?, NOW())";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, ticketId);
        pstmt.setDouble(2, amount);
        pstmt.setString(3, method);

        pstmt.executeUpdate();
    }
}
