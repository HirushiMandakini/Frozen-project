package org.example.model;

import org.example.db.Dbconnection;
import org.example.dto.orderDto;

import java.sql.*;

public class OrderFormModel {
    public String generateNextOrderId() throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "SELECT o_id FROM orders ORDER BY o_id DESC LIMIT 1";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private String splitOrderId(String currentOrderId) {
        if(currentOrderId != null) {
            String[] split = currentOrderId.split("O0");

            int id = Integer.parseInt(split[1]); //01
            id++;
            return "O00" + id;
        } else {
            return "O001";
        }
    }
    public boolean addOrder(orderDto dto) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "INSERT INTO orders VALUES(?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getO_id());
        pstm.setDate(2, (Date) dto.getDate());
        pstm.setString(3, dto.getPayment_id() );
        pstm.setString(3, dto.getDeli_id());
        pstm.setString(4, dto.getCus_id());

        boolean isAdded = pstm.executeUpdate() > 0;
        return isAdded;
    }
    }

