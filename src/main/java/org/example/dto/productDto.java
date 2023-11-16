package org.example.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class productDto {
    private String id;
    private String name;
    private double price;

    public String getCode() {
        return null;
    }
}
