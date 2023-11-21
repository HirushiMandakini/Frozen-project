package org.example.model;

import org.example.db.Dbconnection;
import org.example.dto.tm.cartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderProductModel {
        public static boolean saveOrderProductModel(String o_id, List<cartTm> cartTmList) throws SQLException {
            for(cartTm tm : cartTmList) {
                if(!addOrderProductModel(o_id, tm)) {
                    return false;
                }
            }
            return true;
        }

        private static boolean addOrderProductModel(String o_id, cartTm tm) throws SQLException {
            Connection connection = Dbconnection.getInstance().getConnection();

            String sql = "INSERT INTO order_product_details VALUES(?, ?, ?, ?)";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, tm.getId());
            pstm.setString(2, o_id);
            pstm.setInt(3, tm.getQty());
            pstm.setDouble(4, tm.getUnitPrice());


            return pstm.executeUpdate() > 0;
        }
    }


