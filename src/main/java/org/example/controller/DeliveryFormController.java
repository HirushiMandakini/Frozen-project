package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dto.customerDto;
import org.example.dto.deliveryDto;
import org.example.dto.driverDto;
import org.example.dto.employeeDto;
import org.example.dto.tm.deliveryTm;
import org.example.model.CustomerFormModel;
import org.example.model.DeliveryFormModel;
import org.example.model.DriverFormModel;


import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.example.db.Dbconnection.email;


public class DeliveryFormController {
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
    public TextField txtDeliveryId;
    @FXML
    private Label lblDeliveryId;
    private DeliveryFormModel deliveryModel = new DeliveryFormModel();

    public void initialize() throws SQLException {
        setCellValueFactory();
        loadAllDelivery();
        generateNextDeliveryId();
        loadDriverIds();

    }

    private void setCellValueFactory() {
        col1Id.setCellValueFactory(new PropertyValueFactory<>("deli_id"));
        col2DeliveryAddress.setCellValueFactory(new PropertyValueFactory<>("deli_address"));
        col3DriverId.setCellValueFactory(new PropertyValueFactory<>("driver_id"));
    }

    private void loadAllDelivery() {
        var model = new DeliveryFormModel();

        ObservableList<deliveryTm> obList = FXCollections.observableArrayList();

        try {
            List<deliveryDto> dtoList = model.getAllDelivery();
            for (deliveryDto dto : dtoList) {
                obList.add(
                        new deliveryTm(
                                dto.getDeli_id(),
                                dto.getDeli_address(),
                                dto.getDriver_id()
                        )
                );
            }
            tblDelivery.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNextDeliveryId() throws SQLException {
        String deli_id = DeliveryFormModel.generateNextDeliveryId();
        lblDeliveryId.setText(deli_id);
    }

    private void loadDriverIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<driverDto> idList = DriverFormModel.getAllDriver();

            for (driverDto dto : idList) {
                obList.add(dto.getId());
            }

            cmbDriverId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void btnDelAddOnAction(ActionEvent actionEvent) {
        String deli_id = lblDeliveryId.getText();
        String deli_address = txtDeliveryAddress.getText();
        String driver_id = cmbDriverId.getValue();

        var dto = new deliveryDto(deli_id, deli_address, driver_id);
        try {
            boolean isSaved = DeliveryFormModel.addDelivery(dto);
            if (isSaved) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "delivery saved!");
                alert.showAndWait();
                loadAllDelivery();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong!!!");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
       // setRemoveBtnAction(btn);

    }

    public void cmbDriverOnAction(ActionEvent actionEvent) {
        String id = cmbDriverId.getValue();

        try {
            driverDto driverDto =DriverFormModel.searchDriver(id);
            lblDriverName.setText(driverDto.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void btnDelUpdateOnAction(ActionEvent actionEvent) {

    }
   /* private void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblDelivery.getSelectionModel().getSelectedIndex();

               // obList.remove(focusedIndex);
                tblDelivery.refresh();
            }
        });
    }*/
}

