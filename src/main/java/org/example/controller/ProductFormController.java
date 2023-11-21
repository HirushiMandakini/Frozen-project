package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dto.productDto;
import org.example.dto.tm.productTm;
import org.example.model.ProductFormModel;
import org.example.controller.ProductFormController;

import java.sql.SQLException;
import java.util.List;


public class ProductFormController {

        @FXML
        public TableColumn<?, ?> col1Id;

        public TableColumn<?, ?> col2NAme;
        @FXML
        public TableColumn<?, ?> col3Price;
        @FXML
        public TextField pId;
        @FXML
        public TextField pName;
        @FXML
        public TextField pPrice;
        @FXML
        public Button proAdd;
        @FXML
        public Button proClear;
        @FXML
        public Button proDelete;
        @FXML
        public Button proUpdate;
        @FXML
        public TableView<productTm> tblProduct;
        public void initialize() {
                setCellValueFactory();
                loadAllProduct();
                tableListener();
        }
        private void loadAllProduct() {
                ObservableList<productTm> obList = FXCollections.observableArrayList();
                try {
                        List<productDto> dtoList = ProductFormModel.loadAllProduct();

                        for (productDto dto : dtoList) {
                                obList.add(new productTm(
                                        dto.getId(),
                                        dto.getName(),
                                        dto.getPrice()
                                ));
                        }
                        tblProduct.setItems(obList);

                } catch (SQLException e) {
                        throw new RuntimeException(e);
                }
        }
        private void tableListener() {
                tblProduct.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
//            System.out.println(newValue);
                        setData(newValue);
                });
        }
        private void setData(productTm row) {
                pId.setText(row.getP_id());
                pName.setText(row.getP_name());
                pPrice.setText(String.valueOf(row.getPrice()));
        }
        private void setCellValueFactory() {
                col1Id.setCellValueFactory(new PropertyValueFactory<>("p_id"));
                col2NAme.setCellValueFactory(new PropertyValueFactory<>("p_name"));
                col3Price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        }
        @FXML
        void btnProAddOnAction(ActionEvent event) {
                String pIdText = pId.getText();
                String pNameText = pName.getText();
                double unitPrice = Double.parseDouble(pPrice.getText());

                var dto = new productDto(pIdText,pNameText,unitPrice);

                try {
                        boolean isSaved = ProductFormModel.saveProduct(dto);
                        if (isSaved) {
                                new Alert(Alert.AlertType.CONFIRMATION, "product saved!").show();
                                clearFields();
                                initialize();
                        }
                } catch (SQLException e){
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
        }
        private void clearFields() {
                pId.setText("");
                pName.setText("");
                pPrice.setText("");
        }
        @FXML
        void btnProClearOnAction(ActionEvent event) {
                clearFields();
        }
        @FXML
        void btnProDeleteOnAction(ActionEvent event) {

        }
        @FXML
        void btnProUpdateOnAction(ActionEvent event) {
                String p_id = pId.getText();
                String p_name =pName.getText();
                double p_price = Double.parseDouble(pPrice.getText());

                try {
                        boolean isUpdated = ProductFormModel.updateProduct(new productDto(p_id,p_name, p_price));
                        if (isUpdated) {
                                new Alert(Alert.AlertType.CONFIRMATION, "item updated").show();
                        }
                } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
        }
}
