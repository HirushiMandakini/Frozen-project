package org.example.model;

import org.example.db.Dbconnection;
import org.example.dto.driverDto;
import org.example.model.DriverFormModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverFormModel {
    public List<driverDto> getAllDriver() throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "SELECT * FROM driver";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<driverDto> dtoList = new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(
                    new driverDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }
        return dtoList;
    }
    public static boolean addDriver(driverDto dto) throws SQLException{
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "INSERT INTO driver (driver_id,driver_name,driver_address,contact_num) VALUES (?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4,dto.getContact_num());

        boolean isAdded = pstm.executeUpdate()>0;
        return isAdded;

    }
    public boolean deleteDriver (String id) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "DELETE FROM driver WHERE driver_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }
    public boolean updateDriver (driverDto dto) throws SQLException{
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "UPDATE driver SET driver_name = ?,driver_address = ?,contact_num =? WHERE driver_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3, dto.getContact_num());
        pstm.setString(4,dto.getId());

        return pstm.executeUpdate() > 0;
    }
}
