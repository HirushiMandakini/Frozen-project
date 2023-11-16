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
import org.example.model.CustomerFormModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


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
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAdd.getText();
        String email = txtEmail.getText();
        String contact_num=txtCon.getText();

        var dto = new customerDto(id, name, address, email,contact_num);
        try {
            boolean isSaved = cusModel.addCustomer(dto);
            if (isSaved) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "customer saved!");
                alert.showAndWait();
                loadAllCustomer();
                clearFields();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Something went wrong!!!");
                alert.showAndWait();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
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

        var dto = new customerDto(id, name, address, email,contact_num);
        try {
            boolean isUpdated = cusModel.updateCustomer(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                loadAllCustomer();
                clearFields();
            }  else {
            new Alert(Alert.AlertType.CONFIRMATION, "customer not updated!").show();
        }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    }




