package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CashierNavigationbarFormController {

    @FXML
    private AnchorPane subRoot;

    public void navigate() throws IOException {
        FXMLLoader loader = FXMLLoader.load(getClass().getResource("/view/"));
    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        this.subRoot.getChildren().clear();
        this.subRoot.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml")));

    }

    public void btnInventoryOnAction(ActionEvent actionEvent) throws IOException {
        this.subRoot.getChildren().clear();
        this.subRoot.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/inventory_form.fxml")));

    }

    public void btnOrderOnAction(ActionEvent actionEvent) throws IOException {
        this.subRoot.getChildren().clear();
        this.subRoot.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/order_form.fxml")));

    }

    public void btnDriverOnAction(ActionEvent actionEvent) throws IOException {
        this.subRoot.getChildren().clear();
        this.subRoot.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/driver_form.fxml")));

    }

    public void btnVehicleOnAction(ActionEvent actionEvent) throws IOException {
        this.subRoot.getChildren().clear();
        this.subRoot.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/driver_form.fxml")));

    }

    public void btnDeliveryOnAction(ActionEvent actionEvent) throws IOException {
        this.subRoot.getChildren().clear();
        this.subRoot.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/delivery_form.fxml")));

    }
}
