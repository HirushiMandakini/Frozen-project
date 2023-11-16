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

public class  ProductFormController {

        @FXML
        public TableColumn<?, ?> col1Id;

        @FXML
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
        public Button proDelete;

        @FXML
        public Button proUpdate;
         @FXML
         public Button proClear;

        @FXML
        public TableView<productTm> tblProduct;

        private ProductFormModel proModel = new ProductFormModel();

        public void initialize(){
            setCellValueFactory();
            loadAllProduct();
        }
        private void setCellValueFactory() {
            col1Id.setCellValueFactory(new PropertyValueFactory<>("p_id"));
            col2NAme.setCellValueFactory(new PropertyValueFactory<>("p_name"));
            col3Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        }
        private void loadAllProduct() {
            var model = new ProductFormModel();

            ObservableList<productTm> obList;
            obList = FXCollections.observableArrayList();

            try{
                List<productDto> dtoList = model.getAllProduct();

                for (productDto dto : dtoList){
                    obList.add(
                            new productTm(
                                    dto.getId(),
                                    dto.getName(),
                                    dto.getPrice()
                            )
                    );
                }
                tblProduct.setItems(obList);
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    public void clearFields(){
        pId.setText("");
        pName.setText("");
        pPrice.setText("");
    }
    public void btnProClearOnAction(ActionEvent actionEvent) {

            clearFields();
    }
        @FXML
        void btnProAddOnAction(ActionEvent event) {
            String id = pId.getText();
            String name = pName.getText();
            double price =Double.parseDouble(pPrice.getText());

            var dto = new productDto(id, name, price);
            try {
                boolean isAdded = proModel.addProduct(dto);
                if (isAdded){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "product saved!");
                    alert.showAndWait();
                    loadAllProduct();
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
        @FXML
        void btnProDeleteOnAction(ActionEvent event) {
            String id = pId.getText();

            try {
                boolean isDeleted = proModel.deleteProduct(id);
                if (isDeleted){
                    new Alert(Alert.AlertType.CONFIRMATION, "product deleted!").show();
                    loadAllProduct();
                    clearFields();
                }else{
                    new Alert(Alert.AlertType.CONFIRMATION, "product not deleted!").show();
                }
            } catch(SQLException e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

        @FXML
        void btnProUpdateOnAction(ActionEvent event) {
            String id = pId.getText();
            String name = pName.getText();
            double price = Double.parseDouble(pPrice.getText());

            var dto = new productDto(id,name,price);
            try {
                boolean isUpdated = proModel.updateProduct(dto);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "product updated!").show();
                    loadAllProduct();
                    clearFields();
                }  else {
                    new Alert(Alert.AlertType.CONFIRMATION, "product not updated!").show();
                }
            }catch(SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
        }
