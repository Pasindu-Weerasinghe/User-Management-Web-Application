package com.example.UserManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {


    private int userId;
    private String userName;
    private String userAddress;
    private String userMNumber;
    private MultipartFile userImage;
}
