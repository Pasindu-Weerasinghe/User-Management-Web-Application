package com.example.UserManagement.controller;

import com.example.UserManagement.dto.ResponseDTO;
import com.example.UserManagement.dto.UserDTO;
import com.example.UserManagement.service.UserService;
import com.example.UserManagement.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResponseDTO responseDTO;
    @PostMapping(value = "/saveUser")
    public ResponseEntity saveUser(@RequestBody UserDTO userDTO){
      try{
          String res = userService.saveUser(userDTO);
          if (res.equals("00")){
              responseDTO.setCode(VarList.RSP_SUCCESS);
              responseDTO.setMessage("Success");
              responseDTO.setContent(userDTO);
              return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
          }else if(res.equals("06")){
              responseDTO.setCode(VarList.RSP_DUPLICATE);
              responseDTO.setMessage("User registered");
              responseDTO.setContent(userDTO);
              return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
          }else {
              responseDTO.setCode(VarList.RSP_FAIL);
              responseDTO.setMessage("Error");
              responseDTO.setContent(null);
              return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
          }
      }catch (Exception ex){
          responseDTO.setCode(VarList.RSP_ERROR);
          responseDTO.setMessage(ex.getMessage());
          responseDTO.setContent(null);
          return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @PutMapping(value = "/updateUser")
    public ResponseEntity updateUser(@RequestBody UserDTO userDTO){
        try{
            String res = userService.updateUser(userDTO);
            if (res.equals("00")){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(userDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            }else if(res.equals("01")){
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("User not registered");
                responseDTO.setContent(userDTO);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }else {
                responseDTO.setCode(VarList.RSP_FAIL);
                responseDTO.setMessage("Error");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(ex);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getAllUsers")
    public ResponseEntity getAllUsers(){
        try{
           List<UserDTO> userDTOList = userService.getAllUser();
            responseDTO.setCode(VarList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(userDTOList);
            return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
        }catch (Exception ex){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(ex);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/searchUser/{userId}")
    public ResponseEntity searchUser(@PathVariable int userId){
        try{
            UserDTO userDTO = userService.searchUser(userId);
            if (userDTO != null){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(userDTO);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            }else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("User not registered");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(ex);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(value = "/deleteUser/{userId}")
    public ResponseEntity deleteUser(@PathVariable int userId){
        try{
            String res = userService.deleteUser(userId);
            if (res.equals("00")){
                responseDTO.setCode(VarList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
            }else {
                responseDTO.setCode(VarList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("User not registered");
                responseDTO.setContent(null);
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            responseDTO.setCode(VarList.RSP_ERROR);
            responseDTO.setMessage(ex.getMessage());
            responseDTO.setContent(ex);
            return new ResponseEntity(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
