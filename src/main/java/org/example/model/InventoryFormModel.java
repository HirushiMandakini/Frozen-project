package org.example.model;

import org.example.db.Dbconnection;
import org.example.dto.employeeDto;
import org.example.dto.inventoryDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
public class InventoryFormModel {
    public List<inventoryDto> getAllInventory() throws SQLException{
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "SELECT * FROM inventory";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList <inventoryDto> dtoList = new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(
                    new inventoryDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getInt(3)
                    )
            );
        }
        return dtoList;
    }
    public static boolean addInventory(inventoryDto dto) throws SQLException{
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "INSERT INTO inventory (i_id,i_name,qty) VALUES (?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setInt(3, dto.getQty());


        boolean isAdded = pstm.executeUpdate()>0;
        return isAdded;

    }
    public boolean deleteInventory (String id) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "DELETE FROM inventory WHERE i_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }
    public boolean updateInventory (inventoryDto dto) throws SQLException{
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "UPDATE  inventory SET i_name = ?,qty = ? WHERE i_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setInt(2, dto.getQty());
        pstm.setString(3, dto.getId());

        return pstm.executeUpdate() > 0;
    }
}
