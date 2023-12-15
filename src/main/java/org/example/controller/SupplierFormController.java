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
import java.util.regex.Pattern;

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

    public void initialize() {
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

        try {
            List<supplierDto> dtoList = model.getAllSupplier();
            for (supplierDto dto : dtoList) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSupClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void clearFields() {
        supId.setText("");
        supName.setText("");
        supAddress.setText("");
        supEmail.setText("");
        supCon.setText("");
    }

    @FXML
    void btnSupAddOnAction(ActionEvent event) {

        boolean isSupplierIDValidated = ValidateSupplier();
        boolean isSupplierNameValidated = ValidateSupplier();
        boolean isSupplierAddressValidated = ValidateSupplier();
        boolean isSupplierTelValidated = ValidateSupplier();
        boolean isSupplierEmailValidated = ValidateSupplier();


        if (isSupplierIDValidated && isSupplierNameValidated && isSupplierAddressValidated && isSupplierTelValidated && isSupplierEmailValidated) {
            String id = supId.getText();
            String name = supName.getText();
            String address = supAddress.getText();
            String contact_num = supCon.getText();
            String email = supEmail.getText();

            var dto = new supplierDto(id, name, address, contact_num,email);
            try {
                boolean isAdded = supModel.addSupplier(dto);
                if (isAdded) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "supplier saved!");
                    alert.showAndWait();
                    loadAllSupplier();
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

    private boolean ValidateSupplier() {
        //validate customer id//    C001

        String idText = supId.getText();
        boolean isCustomerIDValidated = Pattern.compile("[S][0-9]{3,}").matcher(idText).matches();
        // boolean  isCustomerIDValidated  =Pattern.matches("[C][0-9]{3}",idText);
        if (!isCustomerIDValidated) {
            new Alert(Alert.AlertType.ERROR, "invalid supplier id!").show();
            return false;
        }
        String nameText = supName.getText();
        boolean isCustomerNameValidated = Pattern.compile("[A-z](.*)").matcher(nameText).matches();
        // boolean  isCustomerNameValidated  =Pattern.matches("[A-z][a-z]",nameText);
        if (!isCustomerNameValidated) {
            new Alert(Alert.AlertType.ERROR, "invalid supplier name!").show();
            return false;
        }
        String addressText = supAddress.getText();
        boolean isCustomerAddressValidated = Pattern.compile("[A-z]{5,}").matcher(addressText).matches();
        // boolean  isCustomerAddressValidated  =Pattern.matches("[A-z][a-z](.*)(city)",addressText);
        if (!isCustomerAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "invalid supplier address!").show();
            return false;
        }
        String contact_num = supCon.getText();
        boolean isCustomerConValidated = Pattern.compile("[0-9]{10,}").matcher(contact_num).matches();
        // boolean  isCustomerConValidated  =Pattern.matches("[0-9]{10}",telText);
        if (!isCustomerConValidated) {
            new Alert(Alert.AlertType.ERROR, "invalid supplier Contact Number!").show();
            return false;
        }
        String emailText = supEmail.getText();
        boolean isCustomerEmailValidated = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])").matcher(emailText).matches();
        // boolean  isCustomerEmailValidated  =Pattern.matches("[0-9]{10}",telText);
        if (!isCustomerEmailValidated) {
            new Alert(Alert.AlertType.ERROR, "invalid supplier Email!").show();
            return false;
        }
        return true;
    }

    @FXML
    void btnSupDeleteOnAction(ActionEvent event) {
        String id = supId.getText();

        try {
            boolean isDeleted = supModel.deleteSupplier(id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier deleted!").show();
                loadAllSupplier();
                clearFields();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier not deleted!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnSupUpdateOnAction(ActionEvent event) {
        String id = supId.getText();
        String name = supName.getText();
        String address = supAddress.getText();
        String contact_num = supCon.getText();
        String email = supEmail.getText();

        var dto = new supplierDto(id, name, address, contact_num,email);
        try {
            boolean isUpdated = supModel.updateSupplier(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier updated!").show();
                loadAllSupplier();
                clearFields();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier not updated!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id = supId.getText();
        try {
            supplierDto dto = SupplierFormModel.searchSupplier(id);
            if (dto != null) {
                supName.setText(dto.getName());
                supAddress.setText(dto.getAddress());
                supCon.setText(dto.getContact_num());
                supEmail.setText(dto.getGmail());
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
