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

        String sql = "INSERT INTO driver (deli_id,deli_address,driver_id) VALUES (?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3,dto.getDriver_id());

        boolean isAdded = pstm.executeUpdate()>0;
        return isAdded;

    }
    public boolean deleteDelivery (String id) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "DELETE FROM delivery WHERE delivery_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }
    public boolean updateDelivery (deliveryDto dto) throws SQLException{
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "UPDATE delivery SET address = ? WHERE delivery_id =?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getAddress());
        pstm.setString(2, dto.getId());

        return pstm.executeUpdate() > 0;
    }

}
