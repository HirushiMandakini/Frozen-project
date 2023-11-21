package org.example.model;

import org.example.db.Dbconnection;
import org.example.dto.placeOrderDto;

import java.sql.*;
import java.time.LocalDate;

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
    public boolean saveOrder(placeOrderDto dto) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "INSERT INTO orders VALUES(?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getO_id());
        pstm.setDate(2, Date.valueOf(dto.getDate()));
        pstm.setString(3, dto.getCus_id() );
        pstm.setString(4, dto.getAmount());


        boolean isAdded = pstm.executeUpdate() > 0;
        return isAdded;
    }


    public boolean saveOrder(String orderId, LocalDate date, String paymentId, String deliveryId, String customerId) {
        return false;
    }
}

