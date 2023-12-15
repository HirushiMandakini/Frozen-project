package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dto.driverDto;
import org.example.dto.supplierDto;
import org.example.dto.tm.supplierTm;
import org.example.dto.tm.vehicleTm;
import org.example.dto.vehicleDto;
import org.example.model.DriverFormModel;
import org.example.model.VehicleFormModel;
import java.sql.SQLException;
import java.util.List;

public class VehicleFormController {

    @FXML
    public TableColumn<?, ?> col1Id;

    @FXML
    public TableColumn<?, ?> col2Vnum;

    @FXML
    public TableColumn<?, ?> col3Model;

    @FXML
    public TableView<vehicleTm> tblVehi;

    @FXML
    public TextField vId;

    @FXML
    public TextField vModel;

    @FXML
    public TextField vNum;

    @FXML
    public Button vehiAdd;

    @FXML
    public Button vehiClear;

    @FXML
    public Button vehiDelete;

    @FXML
    public Button vehiUpdate;
    private VehicleFormModel vehicleModel = new VehicleFormModel();

    public void initialize(){
        setCellValueFactory();
        loadAllVehicle();
    }
    private void setCellValueFactory() {
        col1Id.setCellValueFactory(new PropertyValueFactory<>("v_id"));
        col2Vnum.setCellValueFactory(new PropertyValueFactory<>("v_num"));
        col3Model.setCellValueFactory(new PropertyValueFactory<>("model"));

    }
    private void loadAllVehicle() {
        var model = new VehicleFormModel();

        ObservableList<vehicleTm> obList = FXCollections.observableArrayList();

        try{
            List<vehicleDto> dtoList = model.getAllVehicle();
            for (vehicleDto dto : dtoList){
                obList.add(
                        new vehicleTm(
                                dto.getV_id(),
                                dto.getV_num(),
                                dto.getModel()
                        )
                );
            }
            tblVehi.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void clearFields(){
        vId.setText("");
        vModel.setText("");
        vNum.setText("");
    }
    @FXML
    void btnVehiClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnVehiAddOnAction(ActionEvent event) {
        String id = vId.getText();
        String v_num = vNum.getText();
        String model = vModel.getText();

        var dto = new vehicleDto(id,model,v_num);
        try {
            boolean isAdded = vehicleModel.addVehicle(dto);
            if (isAdded){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "vehicle saved!");
                alert.showAndWait();
                loadAllVehicle();
                clearFields();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR,"Something went wrong!!!");
                alert.showAndWait();
                clearFields();
            }
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
    void btnVehiDeleteOnAction(ActionEvent event) {
        String id = vId.getText();

        try {
            boolean isDeleted = vehicleModel.deleteVehicle(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "vehicle deleted!").show();
                loadAllVehicle();
                clearFields();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION, "vehicle not deleted!").show();
            }
        } catch(SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnVehiUpdateOnAction(ActionEvent event) {
        String id = vId.getText();
        String model = vModel.getText();
        String v_num = vNum.getText();

        vehicleDto dto = new vehicleDto(id, model,v_num);
        try {
            boolean isUpdated = vehicleModel.updateVehicle(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "vehicle updated!").show();
                loadAllVehicle();
                clearFields();
            }  else {
                new Alert(Alert.AlertType.CONFIRMATION, "vehicle not updated!").show();
            }
        }catch(SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String vIdText = vId.getText();
        try {
            vehicleDto dto =VehicleFormModel.searchVehicle(vIdText);
            if (dto != null) {
                vNum.setText(dto.getV_num());
                vModel.setText(dto.getModel());
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
