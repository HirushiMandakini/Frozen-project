package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.example.dto.customerDto;
import org.example.dto.tm.customerTm;
import org.example.mail.mail;
import org.example.model.CustomerFormModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;


public class CustomerFormController {
    @FXML
    public Button cusAdd;
    @FXML
    public Button cusDelete;
    @FXML
    public Button cusUpdate;
    public TextField txtId;
    public TextField txtName;
    public TextField txtAdd;
    public TextField txtEmail;
    public TextField txtCon;
    @FXML
    public TableColumn<?, ?> colAdd;

    @FXML
    public TableColumn<?, ?> colCon;

    @FXML
    public TableColumn<?, ?> colEmail;

    @FXML
    public TableColumn<?, ?> colId;

    @FXML
    public TableColumn<?, ?> colName;
    @FXML
    public TableView<customerTm> tblCustomer;

    private CustomerFormModel cusModel = new CustomerFormModel();
    private customerDto dto;

    public void initialize(){
        setCellValueFactory();
        loadAllCustomer();
    }
    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAdd.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("gmail"));
        colCon.setCellValueFactory(new PropertyValueFactory<>("contact_num"));
    }
    private void loadAllCustomer() {
        var model = new CustomerFormModel();

        ObservableList<customerTm>obList = FXCollections.observableArrayList();

        try{
            List<customerDto>dtoList = model.getAllCustomer();
            for (customerDto dto : dtoList){
                obList.add(
                  new customerTm(
                          dto.getId(),
                          dto.getName(),
                          dto.getAddress(),
                          dto.getEmail(),
                          dto.getContact_num()
                          )
                );
            }
            tblCustomer.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void btnCusClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }
    public void clearFields(){
        txtId.setText("");
        txtName.setText("");
        txtAdd.setText("");
        txtEmail.setText("");
        txtCon.setText("");
    }
    public void btnCusAddOnAction(ActionEvent actionEvent) {
        boolean isCustomerIDValidated = ValidateCustomer();
        boolean isCustomerNameValidated = ValidateCustomer();
        boolean isCustomerAddressValidated = ValidateCustomer();
        boolean isCustomerTelValidated = ValidateCustomer();
        boolean isCustomerEmailValidated = ValidateCustomer();

        if (isCustomerIDValidated && isCustomerNameValidated && isCustomerAddressValidated && isCustomerTelValidated && isCustomerEmailValidated) {
            String id = txtId.getText();
            String name = txtName.getText();
            String address = txtAdd.getText();
            String email = txtEmail.getText();
            String contact_num = txtCon.getText();

            var dto = new customerDto(id, name, address, email, contact_num);
            try {
                boolean isSaved = cusModel.addCustomer(dto);
                if (isSaved) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "customer saved!");
                    alert.showAndWait();

                    mail mail = new mail();
                    mail.setMsg("\uD83C\uDF89 Congratulations! As a cherished loyalty customer at Frozen Juicebar, you've unlocked exclusive benefits and discounts. Get ready for a summer filled with flavorful delights! \uD83C\uDF79");
                    mail.setTo(email);
                    mail.setSubject("loyality customer");
                    Thread thread = new Thread(mail);
                    thread.start();
                    loadAllCustomer();
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
    private boolean ValidateCustomer() {
        //validate customer id//    C001

        String idText = txtId.getText();
        boolean isCustomerIDValidated = Pattern.compile("[C][0-9]{3,}").matcher(idText).matches();
        // boolean  isCustomerIDValidated  =Pattern.matches("[C][0-9]{3}",idText);
        if (!isCustomerIDValidated) {
            new Alert(Alert.AlertType.ERROR, "invalid customer id!").show();
            return false;
        }
        String nameText = txtName.getText();
        boolean isCustomerNameValidated = Pattern.compile("[A-z](.*)").matcher(nameText).matches();
        // boolean  isCustomerNameValidated  =Pattern.matches("[A-z][a-z]",nameText);
        if (!isCustomerNameValidated) {
            new Alert(Alert.AlertType.ERROR, "invalid customer name!").show();
            return false;
        }
        String addressText = txtAdd.getText();
        boolean isCustomerAddressValidated = Pattern.compile("[A-z]{5,}").matcher(addressText).matches();
        // boolean  isCustomerAddressValidated  =Pattern.matches("[A-z][a-z](.*)(city)",addressText);
        if (!isCustomerAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "invalid customer address!").show();
            return false;
        }

        String contact_num = txtCon.getText();
        boolean isCustomerConValidated = Pattern.compile("[0-9]{10,}").matcher(contact_num).matches();
        // boolean  isCustomerConValidated  =Pattern.matches("[0-9]{10}",telText);
        if (!isCustomerConValidated) {
            new Alert(Alert.AlertType.ERROR, "invalid customer Contact Number!").show();
            return false;
        }
        String emailText = txtEmail.getText();
        boolean isCustomerEmailValidated = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])").matcher(emailText).matches();
        // boolean  isCustomerEmailValidated  =Pattern.matches("[0-9]{10}",telText);
        if (!isCustomerEmailValidated) {
            new Alert(Alert.AlertType.ERROR, "invalid customer Email!").show();
            return false;
        }
        return true;
}


    public void btnCusDeleteOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        try {
            boolean isDeleted = cusModel.deleteCustomer(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                loadAllCustomer();
                clearFields();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION, "customer not deleted!").show();
            }
        } catch(SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void btnCusUpdateOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAdd.getText();
        String email = txtEmail.getText();
        String contact_num = txtCon.getText();

        var dto = new customerDto(id, name, address, email, contact_num);
        try {
            boolean isUpdated = cusModel.updateCustomer(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                loadAllCustomer();
                clearFields();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "customer not updated!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        try {
            customerDto dto = CustomerFormModel.searchCustomer(id);
            if (dto != null) {
                txtName.setText(dto.getName());
                txtAdd.setText(dto.getAddress());
                txtEmail.setText(dto.getEmail());
                txtCon.setText(dto.getContact_num());
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






