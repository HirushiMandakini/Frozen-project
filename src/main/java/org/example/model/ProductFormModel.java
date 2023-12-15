package org.example.model;

import org.example.db.Dbconnection;
import org.example.dto.productDto;
import org.example.dto.supplierDto;
import org.example.dto.tm.cartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductFormModel {
    private cartTm[] cartTm;

    public static List<productDto> loadAllProduct() throws SQLException {
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

    public static boolean saveProduct(productDto dto) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "INSERT INTO products VALUES(?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getP_id());
        pstm.setString(2, dto.getP_name());
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
   public static productDto searchProduct(String id) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();
        String sql = "SELECT * FROM products WHERE p_id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        productDto dto = null;

        if(resultSet.next()) {
            dto = new productDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            );
        }
        return dto;
    }
    public static boolean updateProduct(productDto dto) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "UPDATE products SET p_name = ?,price = ? WHERE p_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getP_name());
        pstm.setDouble(2, dto.getPrice());
        pstm.setString(3, dto.getP_id());

        boolean isUpdated = pstm.executeUpdate() > 0;
        return isUpdated;
    }

}


