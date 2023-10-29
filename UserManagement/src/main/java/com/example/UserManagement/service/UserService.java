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

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;
    public String saveUser(UserDTO userDTO){
        if(userRepo.existsById(userDTO.getUserId())){
            return VarList.RSP_DUPLICATE;
        }else{
            userRepo.save(modelMapper.map(userDTO, User.class));
            return VarList.RSP_SUCCESS;
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
