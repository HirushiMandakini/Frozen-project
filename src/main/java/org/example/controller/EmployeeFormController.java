package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dto.customerDto;
import org.example.dto.employeeDto;
import org.example.dto.tm.employeeTm;
import org.example.model.CustomerFormModel;
import org.example.model.EmployeeFormModel;

import java.sql.SQLException;
import java.util.List;

public class EmployeeFormController {

    @FXML
    private TextField eAdd;

    @FXML
    private TextField eCon;

    @FXML
    private TextField eEmail;

    @FXML
    private TextField eId;

    @FXML
    private TextField eName;

    @FXML
    private Button empAdd;

    @FXML
    private TableColumn<?, ?> empCol1;

    @FXML
    private TableColumn<?, ?> empCol2;

    @FXML
    private TableColumn<?, ?> empCol3;

    @FXML
    private TableColumn<?, ?> empCol4;

    @FXML
    private TableColumn<?, ?> empCol5;

    @FXML
    private Button empDelete;

    @FXML
    private TableView<employeeTm> empTbl;

    @FXML
    private Button empUpdate;
    @FXML
    private Button empClear;

    private EmployeeFormModel empModel = new EmployeeFormModel();

    public void initialize(){
        setCellValueFactory();
        loadAllEmployee();
    }
    private void setCellValueFactory() {
        empCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        empCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        empCol3.setCellValueFactory(new PropertyValueFactory<>("address"));
        empCol4.setCellValueFactory(new PropertyValueFactory<>("gmail"));
        empCol5.setCellValueFactory(new PropertyValueFactory<>("contact_num"));
    }
    private void loadAllEmployee() {
        var model = new EmployeeFormModel();

        ObservableList<employeeTm> obList = FXCollections.observableArrayList();

        try{
            List<employeeDto> dtoList = model.getAllEmployee();
            for (employeeDto dto : dtoList){
                obList.add(
                        new employeeTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getGmail(),
                                dto.getContact_num()
                        )
                );
            }
            empTbl.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void btnEmpClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }
    public void clearFields(){
        eId.setText("");
        eName.setText("");
        eAdd.setText("");
        eEmail.setText("");
        eCon.setText("");
    }
    @FXML
    void btnEmpAddOnAction(ActionEvent event) {
        String id = eId.getText();
        String name = eName.getText();
        String address = eAdd.getText();
        String email = eEmail.getText();
        String contact_num=eCon.getText();

        var dto = new employeeDto(id, name, address, email,contact_num);
        try {
            boolean isSaved = empModel.addEmployee(dto);
            if (isSaved){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "employee saved!");
                alert.showAndWait();
                loadAllEmployee();
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
    void btnEmpDeleteOnAction(ActionEvent event) {
        String id = eId.getText();

        try {
            boolean isDeleted = empModel.deleteEmployee(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "employee deleted!").show();
                loadAllEmployee();
                clearFields();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION, "employee not deleted!").show();
            }
        } catch(SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
    void btnEmpUpdateOnAction(ActionEvent event) {
        String id = eId.getText();
        String name = eName.getText();
        String address = eAdd.getText();
        String email = eEmail.getText();
        String contact_num = eCon.getText();

        var dto = new employeeDto(id, name, address, email,contact_num);
        try {
            boolean isUpdated = empModel.updateEmployee(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee updated!").show();
                loadAllEmployee();
                clearFields();
            }  else {
                new Alert(Alert.AlertType.CONFIRMATION, "employee not updated!").show();
            }
        }catch(SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    }


