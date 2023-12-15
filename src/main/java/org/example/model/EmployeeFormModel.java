package org.example.model;

import org.example.db.Dbconnection;
import org.example.dto.customerDto;
import org.example.dto.employeeDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;
public class EmployeeFormModel {
    public static List<employeeDto> getAllEmployee() throws SQLException{
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "SELECT * FROM user";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList <employeeDto> dtoList = new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(
                    new employeeDto(
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
    public static boolean addEmployee(employeeDto dto) throws SQLException{
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "INSERT INTO user (u_id,u_name,u_address,u_gmail,contact_num) VALUES (?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4, dto.getGmail());
        pstm.setString(5,dto.getContact_num());

        boolean isAdded = pstm.executeUpdate()>0;
        return isAdded;

    }
    public boolean deleteEmployee (String id) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "DELETE FROM user WHERE u_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }
    public boolean updateEmployee (employeeDto dto) throws SQLException{
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "UPDATE  user SET u_name = ?,u_address = ?,u_gmail =?,contact_num =? WHERE u_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3, dto.getGmail());
        pstm.setString(4, dto.getContact_num());
        pstm.setString(5,dto.getId());

        return pstm.executeUpdate() > 0;
    }
    public static employeeDto searchEmployee(String id) throws Exception {
        Connection connection= Dbconnection.getInstance().getConnection();
        String sql="SELECT * FROM user WHERE u_id=?";
        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setObject(1,id);
        employeeDto dto=new employeeDto();
        ResultSet rst=pstm.executeQuery();
        if(rst.next()){
            dto.setId(rst.getString(1));
            dto.setName(rst.getString(2));
            dto.setAddress(rst.getString(3));
            dto.setGmail(rst.getString(4));
            dto.setContact_num(rst.getString(5));
        }
        return dto;
    }
}
