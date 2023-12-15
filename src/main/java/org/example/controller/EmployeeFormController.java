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
import java.util.regex.Pattern;

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
        boolean isEmployeeIDValidated = ValidateEmployee();
        boolean isEmployeeNameValidated = ValidateEmployee();
        boolean isEmployeeAddressValidated = ValidateEmployee();
        boolean isEmployeeTelValidated = ValidateEmployee();
        boolean isEmployeeGmailValidated = ValidateEmployee();

        if (isEmployeeIDValidated && isEmployeeNameValidated && isEmployeeAddressValidated && isEmployeeTelValidated && isEmployeeGmailValidated ) {
            String id = eId.getText();
            String name = eName.getText();
            String address = eAdd.getText();
            String email = eEmail.getText();
            String contact_num = eCon.getText();

            var dto = new employeeDto(id, name, address,email,contact_num);
            try {
                boolean isSaved = empModel.addEmployee(dto);
                if (isSaved) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "employee saved!");
                    alert.showAndWait();
                    loadAllEmployee();
                    clearFields();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong!!!");
                    alert.showAndWait();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
    }
        }
        private boolean ValidateEmployee(){

            String idText = eId.getText();
            boolean isEmployeeIDValidated = Pattern.compile("[E][0-9]{3,}").matcher(idText).matches();

            if (!isEmployeeIDValidated) {
                new Alert(Alert.AlertType.ERROR, "invalid employee id!").show();
                return false;
            }
            String nameText = eName.getText();
            boolean isEmployeeNameValidated = Pattern.compile("[A-z]{2,}").matcher(nameText).matches();
            if (!isEmployeeNameValidated) {
                new Alert(Alert.AlertType.ERROR, "invalid employee name!").show();
                return false;
            }
            String addressText = eAdd.getText();
            boolean isEmployeeAddressValidated = Pattern.compile("[A-z]{2,}").matcher(addressText).matches();
            if (!isEmployeeAddressValidated) {
                new Alert(Alert.AlertType.ERROR, "invalid employee address!").show();
                return false;
            }
            String contact_num = eCon.getText();
            boolean isEmployeeConValidated = Pattern.compile("[0-9]{10,}").matcher(contact_num).matches();
            if (!isEmployeeConValidated) {
                new Alert(Alert.AlertType.ERROR, "invalid employee Contact Number!").show();
                return false;
            }
            String emailText = eEmail.getText();
            boolean isEmployeeEmailValidated = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])").matcher(emailText).matches();
            if (!isEmployeeEmailValidated) {
                new Alert(Alert.AlertType.ERROR, "invalid employee Email!").show();
                return false;
            }
            return true;
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

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id = eId.getText();
        try {
            employeeDto dto = EmployeeFormModel.searchEmployee(id);
            if (dto != null) {
                eName.setText(dto.getName());
                eAdd.setText(dto.getAddress());
                eEmail.setText(dto.getGmail());
                eCon.setText(dto.getContact_num());
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


