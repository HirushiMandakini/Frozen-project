package org.example.dto;
import lombok.*;
import org.example.dto.tm.cartTm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class placeOrderDto {
    private String o_id;
    private LocalDate date;
    private String cus_id;
    private String amount;
    private List<cartTm> cartTmList = new ArrayList<>();
}
