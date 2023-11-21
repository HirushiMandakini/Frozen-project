package org.example.model;

import org.example.db.Dbconnection;
import org.example.dto.placeOrderDto;
import org.example.dto.productDto;
import org.example.model.OrderFormModel;
import org.example.model.ProductFormModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class PlaceOrderModel {
    private final OrderFormModel orderFormModel = new OrderFormModel();
    private final ProductFormModel productFormModel = new ProductFormModel();
    private final OrderProductModel orderProductModel = new OrderProductModel();

    public boolean placeOrder(placeOrderDto placeOrderDto) throws SQLException {
        System.out.println(placeOrderDto);

        String orderId = placeOrderDto.getO_id();
        String customerId = placeOrderDto.getCus_id();
        LocalDate date = placeOrderDto.getDate();
        String amount = placeOrderDto.getAmount();

        Connection connection = null;
        try {
            connection = Dbconnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = orderFormModel.saveOrder(placeOrderDto);
            if (isOrderSaved) {
               // boolean isUpdated = ProductFormModel.updateProduct(placeOrderDto.getCartTmList());
               // if (isUpdated) {
                    boolean isOrderProductModelSaved = OrderProductModel.saveOrderProductModel(placeOrderDto.getO_id(), placeOrderDto.getCartTmList());
                    if (isOrderProductModelSaved) {
                        connection.commit();
                    }
              //   }
            }
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return true;
    }
}
