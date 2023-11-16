package org.example.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class orderProductDto {
  private String p_id;
  private String o_id;
  private  int qty;
  private Double unit_price;
}
