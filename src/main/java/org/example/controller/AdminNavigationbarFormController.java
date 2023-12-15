package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;

public class AdminNavigationbarFormController{
        @FXML
        public AnchorPane pane;
        @FXML
        public Button customer;

        @FXML
        public Button delivery;

        @FXML
        public Button driver;

        @FXML
        public Button employee;

        @FXML
        public Button income;

        @FXML
        public Button inventory;

        @FXML
        public Button oder;

        @FXML
        public Button product;

        @FXML
        public AnchorPane subRoot;

        @FXML
        public Button supplier;

        @FXML
        public Button vehicle;

        public void initialize() throws IOException {
                loadPage();
        }

        private void loadPage() throws IOException {
                this.pane.getChildren().clear();
                this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml")));
        }

        @FXML
        void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
                this.pane.getChildren().clear();
                this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml")));

        }
        @FXML
        void btnEmployeeOnAction(ActionEvent event) throws IOException {
                this.pane.getChildren().clear();
                this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/employee_form.fxml")));

        }

        public void btnSupplierOnAction(ActionEvent actionEvent) throws IOException {
                this.pane.getChildren().clear();
                this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/supplier_form.fxml")));

        }

        public void btnProductOnAction(ActionEvent actionEvent) throws IOException {
                this.pane.getChildren().clear();
                this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/product_form.fxml")));

        }

        public void btnInventoryOnAction(ActionEvent actionEvent) throws IOException {
                this.pane.getChildren().clear();
                this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/inventory_form.fxml")));

        }

        public void btnOrderOnAction(ActionEvent actionEvent) throws IOException {
                this.pane.getChildren().clear();
                this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/place_order_form.fxml")));

        }

        public void btnDeliveryOnAction(ActionEvent actionEvent) throws IOException {
                this.pane.getChildren().clear();
                this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/delivery_form.fxml")));

        }

        public void btnDriverOnAction(ActionEvent actionEvent) throws IOException {
                this.pane.getChildren().clear();
                this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/driver_form.fxml")));
        }

        public void btnVehicleOnAction(ActionEvent actionEvent) throws IOException {
                this.pane.getChildren().clear();
                this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/vehicle_form.fxml")));
        }

        public void btnIncomeOnAction(ActionEvent actionEvent) throws IOException {
                this.pane.getChildren().clear();
                this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/report_form.fxml")));
        }
        @FXML
        void btnHomeOnAction(ActionEvent event) throws IOException {
                this.pane.getChildren().clear();
                this.pane.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml")));
        }
}


