package com.example.UserManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

    private int userId;
    private String userName;
    private String userAddress;
    private String userMNumber;
}
