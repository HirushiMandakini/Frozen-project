package org.example.model;

import org.example.db.Dbconnection;
import org.example.dto.customerDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
public class CustomerFormModel {

    public List<customerDto> getAllCustomer() throws SQLException{
            Connection connection = Dbconnection.getInstance().getConnection();

            String sql = "SELECT * FROM customer";
           PreparedStatement pstm = connection.prepareStatement(sql);
           ResultSet resultSet = pstm.executeQuery();

           ArrayList <customerDto> dtoList = new ArrayList<>();

           while (resultSet.next()){
               dtoList.add(
                 new customerDto(
                         resultSet.getString(1),
                         resultSet.getString(2),
                         resultSet.getString(3),
                         resultSet.getString(4),
                         resultSet.getString(5)
               )
               );
           }
           return dtoList;
        }

        public static boolean addCustomer(customerDto dto) throws SQLException{
            Connection connection = Dbconnection.getInstance().getConnection();

            String sql = "INSERT INTO customer VALUES(?,?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, dto.getId());
            pstm.setString(2, dto.getName());
            pstm.setString(3, dto.getAddress());
            pstm.setString(4, dto.getEmail());
            pstm.setString(5,dto.getContact_num());

            boolean isAdded = pstm.executeUpdate()>0;
            return isAdded;

        }
    public boolean deleteCustomer (String id) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "DELETE FROM customer WHERE cus_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }
    public boolean updateCustomer (customerDto dto) throws SQLException{
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "UPDATE customer SET cus_name = ?,cus_address = ?,cus_gmail =?,contact_num =? WHERE cus_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3, dto.getEmail());
        pstm.setString(4, dto.getContact_num());
        pstm.setString(5,dto.getId());

        return pstm.executeUpdate() > 0;

    }

    public static customerDto getCustomerById(String id) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();
        String sql = "SELECT * FROM customer WHERE cus_id =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id );
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            customerDto customerDto = new customerDto();
            customerDto.setId(resultSet.getString(1));
            customerDto.setName(resultSet.getString(2));
            customerDto.setEmail(resultSet.getString(3));
            customerDto.setAddress(resultSet.getString(4));
            customerDto.setContact_num(resultSet.getString(5));
            return customerDto;
        }
        return null;
    }
}
