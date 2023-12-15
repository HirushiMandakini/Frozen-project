package org.example.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class productDto {
    private String p_id;
    private String p_name;
    private double price;

}
