package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.db.Dbconnection;
import org.example.dto.customerDto;
import org.example.dto.productDto;
import org.example.dto.tm.orderTm;
import org.example.model.CustomerFormModel;
import org.example.model.OrderFormModel;
import org.example.model.PlaceOrderModel;
import org.example.model.ProductFormModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.CollationElementIterator;
import java.time.LocalDate;
import java.util.List;

public class PlaceOrderFormController {

    @FXML
    public Button btnClear;

    @FXML
    public Button btnOrderAdd;

    @FXML
    public ComboBox<String> cmboProduct;

    @FXML
    public TableColumn<?, ?> col1ProId;

    @FXML
    public TableColumn<?, ?> col2proName;

    @FXML
    public TableColumn<?, ?> col3Qty;

    @FXML
    public TableColumn<?, ?> col4UnitPrice;

    @FXML
    public TableColumn<?, ?> col5Price;

    @FXML
    public TableColumn<?, ?> col6Action;

    @FXML
    public ComboBox<String> comboCusName;

    @FXML
    public Label lblCusId;

    @FXML
    public Label lblPId;

    @FXML
    public Label lblPrice;

    @FXML
    public Label lblUnitPrice;

    @FXML
    public Label lbloId;

    @FXML
    private Label lblDate;

    @FXML
    public TableView<?> tblOrder;

    @FXML
    public TextField txtQty;

    private CustomerFormModel customerModel = new CustomerFormModel();
    private ProductFormModel productModel = new ProductFormModel();
    private OrderFormModel orderModel = new OrderFormModel();
    private PlaceOrderModel placeOrderModel = new PlaceOrderModel();
    private ObservableList<orderTm> obList = FXCollections.observableArrayList();

    public void initialize() {
        setCellValueFactory();
        generateNextOrderId();
        setDate();
        loadCustomerNames();
        loadProductNames();
    }

    private void setCellValueFactory() {
        col1ProId.setCellValueFactory(new PropertyValueFactory<>("p_id"));
        col2proName.setCellValueFactory(new PropertyValueFactory<>("p_name"));
        col3Qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        col4UnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        col5Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col6Action.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void generateNextOrderId() {
        try {
            String orderId = orderModel.generateNextOrderId();
            lbloId.setText(orderId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void loadProductNames() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<productDto> productDtos = productModel.getAllProduct();

            for (productDto dto : productDtos){
                obList.add(dto.getName());
            }
            cmboProduct.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerNames() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<customerDto> idList = customerModel.getAllCustomer();

            for (customerDto dto : idList) {
                obList.add(dto.getName());
            }
            comboCusName.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbCusNameOnAction(ActionEvent event) {
        String name = comboCusName.getValue();

        try {
            customerDto dto = CustomerFormModel.getCustomerByName(name);
            lblCusId.setText(dto.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbProductOnAction(ActionEvent event) {

    }

    private void setDate() {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }
    @FXML
    void btnAddOnAction(ActionEvent event) {

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

}
