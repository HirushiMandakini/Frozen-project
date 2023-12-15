package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dto.customerDto;
import org.example.dto.productDto;
import org.example.dto.tm.productTm;
import org.example.mail.mail;
import org.example.model.CustomerFormModel;
import org.example.model.ProductFormModel;
import org.example.controller.ProductFormController;
import org.example.model.SupplierFormModel;

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
        private ObservableList<productDto> obList; // Assuming productDto is the type of objects in the list
       // private TableView<productDto> tblProduct; // Assuming tblProduct is your TableView

        private ProductFormModel productModel = new ProductFormModel();

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
                                        dto.getP_id(),
                                        dto.getP_name(),
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

                var dto = new productDto(pIdText, pNameText, unitPrice);

                try {
                        boolean isSaved = ProductFormModel.saveProduct(dto);
                        if (isSaved) {
                                new Alert(Alert.AlertType.CONFIRMATION, "product saved!").show();
                                clearFields();
                                initialize();
                        }
                } catch (SQLException e) {
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
                String id = pId.getText();

                try {
                        boolean isDeleted = productModel.deleteProduct(id);
                        if (isDeleted) {
                                new Alert(Alert.AlertType.CONFIRMATION, "product deleted!").show();
                                loadAllProduct();
                                clearFields();
                        } else {
                                new Alert(Alert.AlertType.CONFIRMATION, "product not deleted!").show();
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
        }

        @FXML
        void btnProUpdateOnAction(ActionEvent event) {
                String p_id = pId.getText();
                String p_name = pName.getText();
                double price = Double.parseDouble(pPrice.getText());

                try {
                        boolean isUpdated = ProductFormModel.updateProduct(new productDto(p_id, p_name, price));
                        if (isUpdated) {
                                new Alert(Alert.AlertType.CONFIRMATION, "product updated").show();
                                initialize();
                                clearFields();
                        }
                } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
        }

        public void btnSearchOnAction(ActionEvent actionEvent) {
                String pIdText = pId.getText();
                try {
                        productDto dto = ProductFormModel.searchProduct(pIdText);
                        if (dto != null) {
                                pName.setText(dto.getP_name());
                                pPrice.setText(String.valueOf(dto.getPrice()));
                        } else {
                                new Alert(Alert.AlertType.WARNING, "Try Again").show();
                        }
                } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                } catch (Exception e) {
                        throw new RuntimeException(e);
                }
        }

     /*   public void btnSendOnAction(ActionEvent actionEvent) throws SQLException {
                List<customerDto> customerDtos = CustomerFormModel.getAllCustomer();
                String Massage = "";
                for (int i = 0; i < tblProduct.getItems().size(); i++) {
                        productDto productDto =  obList.get(i);
                        String name = productDto.getP_name();
                        double price = productDto.getPrice();
                        Massage = Massage + "\n" + name + " - " + price;
                }

                for (customerDto customerDto : customerDtos) {
                        String to = customerDto.getEmail();


                        mail mail = new mail();
                        mail.setMsg(Massage);
                        mail.setTo(to);
                        mail.setSubject("Newly added!!!!");


                        Thread thread = new Thread(mail);
                        thread.start();
                }
        }*/
}