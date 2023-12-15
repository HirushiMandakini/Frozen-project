package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dto.employeeDto;
import org.example.dto.inventoryDto;
import org.example.dto.productDto;
import org.example.dto.tm.inventoryTm;
import org.example.model.InventoryFormModel;
import org.example.controller.InventoryFormController;
import org.example.model.ProductFormModel;

import java.sql.SQLException;
import java.util.List;

import static java.lang.Integer.*;

public class InventoryFormController {

    @FXML
    private TableColumn<?, ?> col1Id;

    @FXML
    private TableColumn<?, ?> col2Name;

    @FXML
    private TableColumn<?, ?> col3Qty;

    @FXML
    private TextField iId;

    @FXML
    private TextField iName;

    @FXML
    private TextField iQty;

    @FXML
    public Button invAdd;

    @FXML
    public Button invClear;

    @FXML
    public Button invDelete;

    @FXML
    public Button invUpdate;

    @FXML
    private TableView<inventoryTm> tblInv;
    private InventoryFormModel inventoryModel = new InventoryFormModel();

    public void initialize(){
        setCellValueFactory();
        loadAllInventory();
    }
    private void setCellValueFactory() {
        col1Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col3Qty.setCellValueFactory(new PropertyValueFactory<>("qty"));

    }
    private void loadAllInventory() {
        var model = new InventoryFormModel();

        ObservableList<inventoryTm> obList = FXCollections.observableArrayList();

        try{
            List<inventoryDto> dtoList = model.getAllInventory();
            for (inventoryDto dto : dtoList){
                obList.add(
                        new inventoryTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getQty()
                        )
                );
            }
            tblInv.setItems(obList);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void btnInvClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }
    public void clearFields(){
        iId.setText("");
        iName.setText("");
        iQty.setText("");
    }
    @FXML
    void btnInvAddOnAction(ActionEvent event) {
        String id = iId.getText();
        String name = iName.getText();
        int qty = parseInt(iQty.getText());

        var dto = new inventoryDto(id, name, qty);
        try {
            boolean isSaved = inventoryModel.addInventory(dto);
            if (isSaved){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "inventory saved!");
                alert.showAndWait();
                loadAllInventory();
                clearFields();
            }else{
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Something went wrong!!!");
                alert.showAndWait();
                clearFields();
            }
        }catch(SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
    void btnInvDeleteOnAction(ActionEvent event) {
        String id = iId.getText();

        try {
            boolean isDeleted = inventoryModel.deleteInventory(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "inventory deleted!").show();
                loadAllInventory();
                clearFields();
            }else{
                new Alert(Alert.AlertType.CONFIRMATION, "inventory not deleted!").show();
            }
        } catch(SQLException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
    void btnInvUpdateOnAction(ActionEvent event) {
        String id = iId.getText();
        String name = iName.getText();
        int qty= parseInt(iQty.getText());


        var dto = new inventoryDto(id, name, qty);
        try {
            boolean isUpdated = inventoryModel.updateInventory(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "inventory updated!").show();
                loadAllInventory();
                clearFields();
            }  else {
                new Alert(Alert.AlertType.CONFIRMATION, "inventory not updated!").show();
            }
        }catch(SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void btnSearchOnAction(ActionEvent actionEvent) {
        String iIdText = iId.getText();
        try {
            inventoryDto dto = InventoryFormModel.searchInventory(iIdText);
            if (dto != null) {
                iName.setText(dto.getName());
                iQty.setText(String.valueOf(dto.getQty()));
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


