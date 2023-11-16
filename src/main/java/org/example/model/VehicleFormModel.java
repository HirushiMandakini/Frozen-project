package org.example.model;

import org.example.db.Dbconnection;
import org.example.dto.employeeDto;
import org.example.dto.vehicleDto;
import org.example.model.VehicleFormModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleFormModel {
    public List<vehicleDto> getAllVehicle() throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "SELECT * FROM vehicle";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        ArrayList<vehicleDto> dtoList = new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(
                    new vehicleDto(
                            resultSet.getString(1),
                            resultSet.getString(4),
                            resultSet.getString(2)
                    )
            );
        }
        return dtoList;
    }
    public static boolean addVehicle(vehicleDto dto) throws SQLException{
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "INSERT INTO vehicle (v_id,v_num,model) VALUES (?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getV_id());
        pstm.setString(2, dto.getV_num());
        pstm.setString(3, dto.getModel());

        boolean isAdded = pstm.executeUpdate()>0;
        return isAdded;

    }
    public boolean deleteVehicle (String id) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "DELETE FROM vehicle WHERE v_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }
    public boolean updateVehicle (vehicleDto dto) throws SQLException{
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "UPDATE  vehicle SET  v_num = ?,model = ? WHERE v_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(2, dto.getV_num());
        pstm.setString(1, dto.getModel());
        pstm.setString(3, dto.getV_id());

        return pstm.executeUpdate() > 0;
    }
}
