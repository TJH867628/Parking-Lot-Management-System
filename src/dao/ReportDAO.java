package dao;

import util.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    public List<String[]> getOccupancyBySpotType() {

        List<String[]> results = new ArrayList<>();

        String sql = "SELECT pst.name, " +
                     "COUNT(ps.id) AS total, " +
                     "SUM(CASE WHEN ps.status = 'occupied' THEN 1 ELSE 0 END) AS occupied " +
                     "FROM parking_spot ps " +
                     "JOIN parking_spot_type pst ON ps.type_id = pst.id " +
                     "GROUP BY pst.name";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {

                String type = rs.getString("name");
                int total = rs.getInt("total");
                int occupied = rs.getInt("occupied");

                double rate = total == 0 ? 0 :
                        (occupied * 100.0 / total);

                results.add(new String[]{
                        type,
                        String.valueOf(total),
                        String.valueOf(occupied),
                        String.format("%.2f %%", rate)
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }
}
