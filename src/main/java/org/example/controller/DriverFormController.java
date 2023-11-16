package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dto.driverDto;
import org.example.dto.employeeDto;
import org.example.dto.tm.driverTm;
import org.example.dto.tm.employeeTm;
import org.example.model.DriverFormModel;
import org.example.model.EmployeeFormModel;

import java.sql.SQLException;
import java.util.List;

public class DriverFormController {

    @FXML
    private TableColumn<?, ?> col1Id;

    @FXML
    private TableColumn<?, ?> col2Name;

    @FXML
    private TableColumn<?, ?> col3Add;

    @FXML
    private TableColumn<?, ?> col4Con;

    @FXML
    private TextField dAddress;

    @FXML
    private TextField dCon;

    @FXML
    private TextField dId;

    @FXML
    private TextField dName;

    @FXML
    public Button driAdd;

    @FXML
    public Button driClear;

    @FXML
    public Button driDelete;

    @FXML
    public Button driUpdate;

    @FXML
    private TableView<driverTm> tblDriver;
    public void clearFields(){
        dId.setText("");
        dName.setText("");
        dAddress.setText("");
        dCon.setText("");
    }
    private DriverFormModel driverModel = new DriverFormModel();

    public void initialize(){
        setCellValueFactory();
        loadAllDriver();
    }
    private void setCellValueFactory() {
        col1Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col3Add.setCellValueFactory(new PropertyValueFactory<>("address"));
        col4Con.setCellValueFactory(new PropertyValueFactory<>("contact_num"));
    }
    private void loadAllDriver() {
        var model = new DriverFormModel();

        ObservableList<driverTm> obList = FXCollections.observableArrayList();

        try{
            List<driverDto> dtoList = model.getAllDriver();
            for (driverDto dto : dtoList){
                obList.add(
                        new driverTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getContact_num()
                        )
                );
            }
            tblDriver.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnDriAddOnAction(ActionEvent event) {
        String id = dId.getText();
        String name = dName.getText();
        String address = dAddress.getText();
        String contact_num=dCon.getText();

        var dto = new driverDto(id, name, address,contact_num);
        try {
            boolean isSaved = driverModel.addDriver(dto);
            if (isSaved){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "driver saved!");
                alert.showAndWait();
                loadAllDriver();
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
    void btnDriClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDriDeleteOnAction(ActionEvent event) {
        String id = dId.getText();

        try {
            boolean isDeleted = driverModel.deleteDriver(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "driver deleted!").show();
                loadAllDriver();
                clearFields();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION, "driver not deleted!").show();
            }
        } catch(SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
    void btnDriUpdateOnAction(ActionEvent event) {
        String id = dId.getText();
        String name = dName.getText();
        String address = dAddress.getText();
        String contact_num = dCon.getText();

        var dto = new driverDto(id, name, address,contact_num);
        try {
            boolean isUpdated = driverModel.updateDriver(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "driver updated!").show();
                loadAllDriver();
                clearFields();
            }  else {
                new Alert(Alert.AlertType.CONFIRMATION, "driver not updated!").show();
            }
        }catch(SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

}
