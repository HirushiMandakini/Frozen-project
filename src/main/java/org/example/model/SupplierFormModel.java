package org.example.model;


import org.example.db.Dbconnection;
import org.example.dto.customerDto;
import org.example.dto.supplierDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierFormModel {
    public List<supplierDto> getAllSupplier() throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<supplierDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(
                    new supplierDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(5),
                            resultSet.getString(4)
                    )
            );
        }
        return dtoList;
    }

    public boolean addSupplier(supplierDto dto) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "INSERT INTO supplier(sup_id,name,address,gmail,contact_num) VALUES(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getAddress());
        pstm.setString(5, dto.getContact_num());
        pstm.setString(4, dto.getGmail());

        boolean isAdded = pstm.executeUpdate() > 0;
        return isAdded;
    }
    public boolean deleteSupplier(String id) throws SQLException{
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "DELETE FROM supplier WHERE sup_id=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setString(1,id);

        boolean isDeleted = pstm.executeUpdate()>0;
        return isDeleted;
    }
    public boolean updateSupplier(supplierDto dto) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "UPDATE supplier SET name = ?,address = ?,gmail = ?,contact_num = ? WHERE sup_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3, dto.getGmail());
        pstm.setString(4, dto.getContact_num());
        pstm.setString(5, dto.getId());

        boolean isUpdated = pstm.executeUpdate() > 0;
        return isUpdated;
    }

}

