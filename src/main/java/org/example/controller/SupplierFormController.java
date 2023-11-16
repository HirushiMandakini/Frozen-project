package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dto.employeeDto;
import org.example.dto.tm.supplierTm;
import org.example.dto.supplierDto;
import org.example.dto.tm.employeeTm;
import org.example.dto.tm.supplierTm;
import org.example.model.EmployeeFormModel;
import org.example.model.SupplierFormModel;


import java.sql.SQLException;
import java.util.List;

public class SupplierFormController {

    @FXML
    private TableColumn<?, ?> col1Id;

    @FXML
    private TableColumn<?, ?> col2Name;

    @FXML
    private TableColumn<?, ?> col3Add;

    @FXML
    private TableColumn<?, ?> col4Email;

    @FXML
    private TableColumn<?, ?> col5Con;
    @FXML
    public Button supClear;

    @FXML
    private Button supAdd;

    @FXML
    private TextField supAddress;

    @FXML
    private TextField supCon;

    @FXML
    public Button supDelete;

    @FXML
    private TextField supEmail;

    @FXML
    private TextField supId;

    @FXML
    private TextField supName;

    @FXML
    private TableView<supplierTm> tblSup;

    private SupplierFormModel supModel = new SupplierFormModel();

    public void initialize(){
        setCellValueFactory();
        loadAllSupplier();
    }
    private void setCellValueFactory() {
        col1Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col3Add.setCellValueFactory(new PropertyValueFactory<>("address"));
        col4Email.setCellValueFactory(new PropertyValueFactory<>("gmail"));
        col5Con.setCellValueFactory(new PropertyValueFactory<>("contact_num"));
    }
    private void loadAllSupplier() {
        var model = new SupplierFormModel();

        ObservableList<supplierTm> obList = FXCollections.observableArrayList();

        try{
            List<supplierDto> dtoList = model.getAllSupplier();
            for (supplierDto dto : dtoList){
                obList.add(
                        new supplierTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getContact_num(),
                                dto.getGmail()
                        )
                );
            }
            tblSup.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void btnSupClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }
    public void clearFields(){
        supId.setText("");
        supName.setText("");
        supAddress.setText("");
        supEmail.setText("");
        supCon.setText("");
    }
    @FXML
    void btnSupAddOnAction(ActionEvent event) {
        String id = supId.getText();
        String name = supName.getText();
        String address = supAddress.getText();
        String email = supEmail.getText();
        String contact_num=supCon.getText();

        var dto = new supplierDto(id, name, address, email,contact_num);
        try {
            boolean isAdded = supModel.addSupplier(dto);
            if (isAdded){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "supplier saved!");
                alert.showAndWait();
                loadAllSupplier();
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
    void btnSupDeleteOnAction(ActionEvent event) {
        String id = supId.getText();

        try {
            boolean isDeleted = supModel.deleteSupplier(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "supplier deleted!").show();
                loadAllSupplier();
                clearFields();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION, "supplier not deleted!").show();
            }
        } catch(SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
    void btnSupUpdateOnAction(ActionEvent event) {
        String id = supId.getText();
        String name = supName.getText();
        String address = supAddress.getText();
        String email = supEmail.getText();
        String contact_num = supCon.getText();

        var dto = new supplierDto(id, name, address, email,contact_num);
        try {
            boolean isUpdated = supModel.updateSupplier(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier updated!").show();
                loadAllSupplier();
                clearFields();
            }  else {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier not updated!").show();
            }
        }catch(SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


}

