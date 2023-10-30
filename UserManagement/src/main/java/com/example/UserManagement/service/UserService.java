package com.example.UserManagement.service;

import com.example.UserManagement.dto.UserDTO;
import com.example.UserManagement.entity.User;
import com.example.UserManagement.repo.UserRepo;
import com.example.UserManagement.util.VarList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;
    public String saveUser(UserDTO userDTO, MultipartFile userImage){
        try {
            if(userRepo.existsById(userDTO.getUserId())){
                return VarList.RSP_DUPLICATE;
            }else{
                userRepo.save(modelMapper.map(userDTO, User.class));
                saveUserImage(userDTO.getUserId(), userImage);
                return VarList.RSP_SUCCESS;
            }
        }catch (Exception ex){
            return VarList.RSP_ERROR;
        }


    }
    private void saveUserImage(int userId, MultipartFile userImage) {

        try {
            String fileName = userId + "_" + userImage.getOriginalFilename();
            String filePath = Paths.get("D:\\My_learn_Project\\User Management Web Application\\UserManagement\\src\\main\\resources\\static\\", fileName).toString();

            userImage.transferTo(new File(filePath));
        } catch (IOException e) {
            // Handle file transfer errors
            throw new RuntimeException("Failed to save image: " + e.getMessage());
        }
    }
    public String updateUser(UserDTO userDTO){
        if(userRepo.existsById(userDTO.getUserId())){
            userRepo.save(modelMapper.map(userDTO, User.class));
            return VarList.RSP_SUCCESS;
        }else{
            userRepo.save(modelMapper.map(userDTO, User.class));
            return  VarList.RSP_NO_DATA_FOUND;
        }
    }

    public List<UserDTO> getAllUser(){
       List<User> userList = userRepo.findAll();
       return modelMapper.map(userList,new TypeToken<ArrayList<UserDTO>>(){}.getType());
    }

    public UserDTO searchUser(int userId){
        if (userRepo.existsById(userId)){
           User user = userRepo.findById(userId).orElse(null);
           return modelMapper.map(user, UserDTO.class);
        }else{
            return null;
        }
    }

    public String deleteUser(int userId){
        if (userRepo.existsById(userId)){
            userRepo.deleteById(userId);
            return VarList.RSP_SUCCESS;
        }else{
            return VarList.RSP_NO_DATA_FOUND;
        }
    }
}
