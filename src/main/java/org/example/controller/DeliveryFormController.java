package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dto.deliveryDto;
import org.example.dto.driverDto;
import org.example.dto.tm.deliveryTm;
import org.example.model.DeliveryFormModel;


import java.awt.*;
import java.sql.SQLException;
import java.util.List;


public class DeliveryFormController{
    @FXML
    public Button delAdd;
    @FXML
    public Button delDelete;
    @FXML
    public Button delUpdate;
    @FXML
    private ComboBox<String> cmbDriverId;
    @FXML
    private Label lblDriverName;
    @FXML
    private TableColumn<?, ?> col1Id;

    @FXML
    private TableColumn<?, ?> col2DeliveryAddress;

    @FXML
    private TableColumn<?, ?> col3DriverId;
    @FXML
    private TableView<deliveryTm> tblDelivery;

    @FXML
    private TextField txtDeliveryAddress;

    @FXML
    private TextField txtDeliveryId;

    public void initialize(){
        setCellValueFactory();
        loadAllDelivery();
    }
    private void setCellValueFactory() {
        col1Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2DeliveryAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        col3DriverId.setCellValueFactory(new PropertyValueFactory<>("driver_id"));
    }
  /*  public void clearFields(){
        DeId.setText("");
        dAddress.setText("");
        dName.setText("");
    }
*/
    private void loadAllDelivery() {
        var model = new DeliveryFormModel();

        ObservableList<deliveryTm> obList = FXCollections.observableArrayList();

        try{
            List<deliveryDto> dtoList = model.getAllDelivery();
            for (deliveryDto dto : dtoList){
                obList.add(
                        new deliveryTm(
                                dto.getId(),
                                dto.getAddress(),
                                dto.getDriver_id()
                        )
                );
            }
            tblDelivery.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

 /*   public void btnDelAddOnAction(ActionEvent actionEvent) {
        String id = txtDeliveryId.getText();
        String address = txtDeliveryAddress.getText();
        String driver_id = cmbDriverId.getValue();

        var dto = new deliveryDto(id,address,driver_id);
        try {
            boolean isSaved = deliveryFormModel.addDelivery(dto);
            if (isSaved){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "dilivery saved!");
                alert.showAndWait();
                loadAllDelivery();
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
    */
    public void btnDelUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnDelDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnDelAddOnAction(ActionEvent actionEvent) {
    }
}
