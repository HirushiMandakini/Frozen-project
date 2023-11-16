package org.example.model;

import org.example.db.Dbconnection;
import org.example.dto.customerDto;
import org.example.dto.productDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductFormModel {
    public List<productDto> getAllProduct() throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "SELECT * FROM products";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<productDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(
                    new productDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getDouble(3)
                    )
            );
        }

        return dtoList;
    }

    public boolean addProduct(productDto dto) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "INSERT INTO products VALUES(?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setDouble(3, dto.getPrice());

        boolean isAdded = pstm.executeUpdate() > 0;
        return isAdded;
    }
    public boolean deleteProduct(String id) throws SQLException{
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "DELETE FROM products WHERE p_id=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setString(1,id);

        boolean isDeleted = pstm.executeUpdate()>0;
        return isDeleted;
    }
    public boolean updateProduct(productDto dto) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "UPDATE products SET p_name = ?,price = ? WHERE p_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setDouble(2, dto.getPrice());
        pstm.setString(3, dto.getId());

        boolean isUpdated = pstm.executeUpdate() > 0;
        return isUpdated;
    }
}


