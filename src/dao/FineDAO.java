package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import model.Iterator.FineIterator;
import model.Iterator.FineSchemeIterator;
import util.DBConnectionUtil;
import model.FineScheme;

public class FineDAO {

    public void insertFine(Connection conn, String licensePlate, double amount, String reason) throws SQLException {
        String sql = "INSERT INTO fine (license_plate, amount, reason, status) VALUES (?, ?, ?, 'unpaid')";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, licensePlate);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, reason);
            pstmt.executeUpdate();
        }
    }

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
    public FineIterator getUnpaidFines(Connection conn) throws SQLException {
        List<String[]> fines = new ArrayList<>();
        String sql = "SELECT license_plate, amount, status, reason FROM fine WHERE status = 'unpaid'";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                fines.add(new String[] {
                        rs.getString("license_plate"),
                        String.format("%.2f", rs.getDouble("amount")),
                        rs.getString("status"),
                        rs.getString("reason")
                });
            }
        }
        return new FineIterator(fines);
    }

    public FineScheme getFineScheme(Connection conn) throws SQLException {

        String sql = "SELECT * FROM fine_scheme WHERE is_active = 1 LIMIT 1";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {

                return new FineScheme(
                        rs.getInt("id"),
                        rs.getString("scheme_type"),
                        rs.getDouble("base_amount"),
                        rs.getDouble("additional_24_48"),
                        rs.getDouble("additional_48_72"),
                        rs.getDouble("above_72"),
                        rs.getDouble("hourly_rate"),
                        rs.getBoolean("is_active"));
            }
        }

        return null;
    }

    // Get unpaid fines for a specific plate (for ExitController/payment dialog)
    public FineIterator getUnpaidFinesByPlate(Connection conn, String licensePlate) throws SQLException {
        List<String[]> fines = new ArrayList<>();
        String sql = "SELECT license_plate, amount, status, reason FROM fine WHERE status = 'unpaid' AND license_plate = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, licensePlate);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                fines.add(new String[] {
                        rs.getString("license_plate"),
                        String.format("%.2f", rs.getDouble("amount")),
                        rs.getString("status"),
                        rs.getString("reason")
                });
            }
        }
        return new FineIterator(fines);
    }

    public double getUnpaidFineAmountByPlate(Connection conn, String licensePlate) throws SQLException {
        String sql = "SELECT SUM(amount) AS total FROM fine WHERE status = 'unpaid' AND license_plate = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, licensePlate);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }
        }
        return 0;
    }

    public double calculateFineAmount(Connection conn, double durationHours) throws SQLException {

        if (durationHours <= 24) {
            return 0; // No fine within 24 hours
        }

        FineScheme scheme = getFineScheme(conn);
        if (scheme == null) {
            return 0;
        }

        double fine = 0;

        switch (scheme.getId()) {

            case 1: // Fixed Fine Scheme
                fine = 50;
                break;

            case 2: // Progressive Fine Scheme

                fine = 50; // First 24 hours

                if (durationHours > 24 && durationHours <= 48) {
                    fine += 100;
                } else if (durationHours > 48 && durationHours <= 72) {
                    fine += 100 + 150;
                } else if (durationHours > 72) {
                    fine += 100 + 150 + 200;
                }
                break;

            case 3: // Hourly Fine Scheme
                if (durationHours <= 24) {
                    fine = 0;
                } else {

                    double overstayedHours = durationHours - 24;

                    long roundedHours = (long) Math.ceil(overstayedHours);

                    fine = roundedHours * 20;
                }
                break;

            default:
                fine = 0;
        }

        return fine;
    }

    public void generateFinesForOverstayedVehicles(Connection conn) {
        String sql = "SELECT v.license_plate, t.entry_time " +
                "FROM ticket t " +
                "JOIN vehicle v ON t.vehicle_id = v.id " +
                "WHERE t.exit_time IS NULL AND t.status = 'active'";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String licensePlate = rs.getString("license_plate");
                Timestamp entryTime = rs.getTimestamp("entry_time");

                long durationMs = System.currentTimeMillis() - entryTime.getTime();
                double durationHours = durationMs / (1000.0 * 60 * 60);

                double fineAmount = calculateFineAmount(conn, durationHours);
                if (hasUnpaidFine(conn, licensePlate)) {
                    double existingFine = getUnpaidFineAmountByPlate(conn, licensePlate);
                    if (fineAmount != existingFine) {
                        updateFineAmount(conn, licensePlate, fineAmount);
                    }
                    continue;
                }

                if (fineAmount > 0) {
                    insertFine(conn, licensePlate, fineAmount, "Overstayed parking duration");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeFineScheme(int newSchemeId) {

        String deactivateAllSql = "UPDATE fine_scheme SET is_active = 0";
        String activateSql = "UPDATE fine_scheme SET is_active = 1 WHERE id = ?";

        try (Connection conn = DBConnectionUtil.getConnection()) {
            PreparedStatement deactivateStmt = conn.prepareStatement(deactivateAllSql);
            PreparedStatement activateStmt = conn.prepareStatement(activateSql);

            deactivateStmt.executeUpdate();

            activateStmt.setInt(1, newSchemeId);
            activateStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public FineSchemeIterator getAllFineSchemes() {
        List<FineScheme> schemes = new ArrayList<>();
        String sql = "SELECT * FROM fine_scheme ORDER BY id";

        try (Connection conn = DBConnectionUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                schemes.add(new FineScheme(
                        rs.getInt("id"),
                        rs.getString("scheme_type"),
                        rs.getDouble("base_amount"),
                        rs.getDouble("additional_24_48"),
                        rs.getDouble("additional_48_72"),
                        rs.getDouble("above_72"),
                        rs.getDouble("hourly_rate"),
                        rs.getBoolean("is_active")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new FineSchemeIterator(schemes);
    }

    public void updateFineAmount(Connection conn, String licensePlate, double amount) {

        String sql = "UPDATE fine SET amount = ? " + "WHERE license_plate = ? AND status = 'unpaid'";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, amount);
            pstmt.setString(2, licensePlate);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getTotalUnpaidFineAmount(){
        String sql = "SELECT SUM(amount) AS total FROM fine WHERE status = 'unpaid'";
        try (Connection conn = DBConnectionUtil.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
}
