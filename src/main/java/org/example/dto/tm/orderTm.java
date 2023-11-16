package org.example.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class orderTm {
    private String product_id;
    private String product_name;
    private int qty;
    private Double Unit_price;
    private Double price;
    private Button btn;
}
