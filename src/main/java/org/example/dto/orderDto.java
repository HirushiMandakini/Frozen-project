package org.example.dto;
import lombok.*;
import org.example.dto.tm.orderTm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class orderDto {
    private String o_id;
    private Date date;
    private String payment_id;
    private String deli_id;
    private String cus_id;
    private List<orderTm> orderTmList = new ArrayList<>();
}
