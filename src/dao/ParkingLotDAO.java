package dao;

import java.util.*;
import model.ParkingLot;
import model.ParkingFloor;
import model.ParkingSpot;
import model.Iterator.FloorIterator;
import model.Iterator.SpotIterator;
import util.DBConnectionUtil;
import java.sql.*;

public class ParkingLotDAO {

    public ParkingLot loadParkingLot() {
        ParkingLot parkingLot = new ParkingLot();

        String sql = "SELECT pf.id AS floor_id, " +
                "pf.number AS floor_number, " +
                "ps.floor_id, " +
                "ps.row_number, " +
                "ps.spot_number, " +
                "pst.name, " +
                "ps.status, " +
                "ps.current_vehicle, " +
                "pst.hourly_rate " +
                "FROM parking_floor pf " +
                "JOIN parking_spot ps ON pf.id = ps.floor_id " +
                "JOIN parking_spot_type pst ON ps.type_id = pst.id " +
                "ORDER BY pf.number, ps.row_number, ps.spot_number";

        try (Connection conn = DBConnectionUtil.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            Map<Integer, ParkingFloor> floorMap = new HashMap<>();

            while (rs.next()) {
                int floor_id = rs.getInt("floor_id");
                int floor_number = rs.getInt("floor_number");
                int spot_id = rs.getInt("spot_id");
                int row_number = rs.getInt("row_number");
                int spot_number = rs.getInt("spot_number");
                String spotType = rs.getString("name");
                String status = rs.getString("status");
                String currentVehicle = rs.getString("current_vehicle");
                Double hourlyRate = rs.getDouble("hourly_rate");

                ParkingFloor floor = floorMap.get(floor_id);
                if (floor == null) {
                    floor = new ParkingFloor(floor_id, floor_number);
                    floorMap.put(floor_id, floor);
                    parkingLot.addFloor(floor);
                }

                ParkingSpot spot = new ParkingSpot(spot_id, floor_id, row_number, spot_number, spotType, status, currentVehicle,hourlyRate);
                floor.addSpot(spot);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return parkingLot;
    }
}
