package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.dto.customerDto;
import org.example.dto.placeOrderDto;
import org.example.dto.productDto;
import org.example.dto.tm.cartTm;
import org.example.model.CustomerFormModel;
import org.example.model.OrderFormModel;
import org.example.model.PlaceOrderModel;
import org.example.model.ProductFormModel;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlaceOrderFormController {

    @FXML
    public Button btnClear;

    @FXML
    public Button btnAddToCart;

    @FXML
    public ComboBox<String> cmboProductId;

    @FXML
    public TableColumn<?, ?> col1ProId;

    @FXML
    public TableColumn<?, ?> col2proName;

    @FXML
    public TableColumn<?, ?> col3Qty;

    @FXML
    public TableColumn<?, ?> col4UnitPrice;

    @FXML
    private TableColumn<?, ?> col5Total;

    @FXML
    public TableColumn<?, ?> col6Action;

    @FXML
    public ComboBox<String> comboCusId;

    @FXML
    public Label lblCusName;

    @FXML
    private Label lblCusId;

    @FXML
    public Label lblPName;

    @FXML
    public Label lblPrice;

    @FXML
    public Label lblUnitPrice;

    @FXML
    public Label lbloId;

    @FXML
    private Label lblDate;

    @FXML
    public TableView<cartTm> tblOrder;

    @FXML
    public TextField txtQty;
    @FXML
    public Label lbnNetTotal;

    private CustomerFormModel customerModel = new CustomerFormModel();
    private ProductFormModel productModel = new ProductFormModel();
    private OrderFormModel orderModel = new OrderFormModel();
    private PlaceOrderModel placeOrderModel = new PlaceOrderModel();
    private ObservableList<cartTm> obList = FXCollections.observableArrayList();
    private cartTm cartTm;
    public void initialize() {
        setCellValueFactory();
        generateNextOrderId();
        setDate();
        loadCustomerIds();
        loadProductIds();
    }

    private void setCellValueFactory() {
        col1ProId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col2proName.setCellValueFactory(new PropertyValueFactory<>("name"));
        col3Qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        col4UnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        col5Total.setCellValueFactory(new PropertyValueFactory<>("Total"));
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

    private void loadProductIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<productDto> productDtos = ProductFormModel.loadAllProduct();

            for (productDto dto : productDtos) {
                obList.add(dto.getId());
            }
            cmboProductId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<customerDto> idList = customerModel.getAllCustomer();

            for (customerDto dto : idList) {
                obList.add(dto.getId());
            }

            comboCusId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDate() {
//        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(LocalDate.now()));
    }
    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String p_id = cmboProductId.getValue();
        String p_name = lblPName.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double netTot = unitPrice * qty;
        Button btn = new Button("Remove");

        setRemoveBtnAction(btn);
        btn.setCursor(Cursor.HAND);

        if (!obList.isEmpty()) {
            for (int i = 0; i < tblOrder.getItems().size(); i++) {
                if (col1ProId.getCellData(i).equals(p_id)) {
                    int col_qty = (int) col3Qty.getCellData(i);
                    qty += col_qty;
                    netTot = unitPrice * qty;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTotal(netTot);;

                    calculateTotal();
                    tblOrder.refresh();
                    return;
                }
            }
        }
        var cartTm = new cartTm(p_id,p_name, qty, unitPrice,netTot,btn);

        obList.add(cartTm);

        tblOrder.setItems(obList);
        calculateTotal();
        txtQty.clear();
    }
    private void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblOrder.getSelectionModel().getSelectedIndex();

                org.example.dto.tm.cartTm remove = obList.remove(focusedIndex);
                tblOrder.refresh();
                calculateTotal();
            }
        });
    }
    private void calculateTotal() {
        double total = 0;
        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            total += (double) col5Total.getCellData(i);
        }
        lbnNetTotal.setText(String.valueOf(total));
    }
    @FXML
    void btnNewCustomerOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/customer_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Customer Manage");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderId = lbloId.getText();
        LocalDate date = LocalDate.parse(lblDate.getText());
        String id = comboCusId.getValue();
        String tot = lbnNetTotal.getText();


        List<cartTm> cartTmList = new ArrayList<>();
        for (int i = 0; i < tblOrder.getItems().size(); i++) {
            cartTm cartTm = obList.get(i);

            cartTmList.add(cartTm);
        }
        var placeOrderDto = new placeOrderDto(orderId,date,id, tot, cartTmList);
        try {
            boolean isSuccess = placeOrderModel.placeOrder(placeOrderDto);
            if (isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Success!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void cmbProductOnAction(ActionEvent event) {
        String code = cmboProductId.getValue();

        txtQty.requestFocus();
        try {
            productDto dto = productModel.searchProduct(code);
            lblPName.setText(dto.getName());
            lblUnitPrice.setText(String.valueOf(dto.getPrice()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void cmbCustomerOnAction(ActionEvent event) {
        String id = comboCusId.getValue();
        try {
            customerDto customerDto = CustomerFormModel.getCustomerById(id);
            lblCusId.setText(customerDto.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);
    }
}


