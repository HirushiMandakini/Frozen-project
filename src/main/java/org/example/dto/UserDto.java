package org.example.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class UserDto {
    private String u_id;
    private String u_password;
    private String u_email;

}
