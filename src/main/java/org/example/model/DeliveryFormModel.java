package org.example.model;

import org.example.db.Dbconnection;
import org.example.dto.deliveryDto;
import org.example.dto.driverDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryFormModel {

    public List<deliveryDto> getAllDelivery() throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "SELECT * FROM delivery";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<deliveryDto> dtoList = new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(
                    new deliveryDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3)
                    )
            );
        }
        return dtoList;
    }
    public static boolean addDelivery(deliveryDto dto) throws SQLException{
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "INSERT INTO delivery VALUES (?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getDeli_id());
        pstm.setString(2, dto.getDeli_address());
        pstm.setString(3, dto.getDriver_id());

        boolean isAdded = pstm.executeUpdate()>0;
        return isAdded;

    }
    /*
    public boolean deleteDelivery (String id) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "DELETE FROM delivery WHERE deli_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }
    public boolean updateDelivery (deliveryDto dto) throws SQLException{
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "UPDATE delivery SET deli_address = ?,driver_id = ? WHERE deli_id =?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getDeli_address());
        pstm.setString(2, dto.getDriver_id());
        pstm.setString(3, dto.getDeli_id());

        return pstm.executeUpdate() > 0;
    }
*/
    public static String generateNextDeliveryId() throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "SELECT deli_id FROM delivery ORDER BY deli_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentOrderId) {
        if(currentOrderId != null) {
            String[] split = currentOrderId.split("D0");
            int id = Integer.parseInt(split[1]); //01
            id++;
            if(id < 10) {
                return "D00" + id;
            } else if (id < 100) {
                return "D0" + id;
            } else {
                return "D" + id;
            }
        } else {
            return "D001";
        }
    }

}
