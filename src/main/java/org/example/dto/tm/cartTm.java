package org.example.dto.tm;
import lombok.*;
import javafx.scene.control.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class cartTm {
    private String id;
    private String name;
    private int qty;
    private double unitPrice;
    private double Total;
    private Button btn;
}
