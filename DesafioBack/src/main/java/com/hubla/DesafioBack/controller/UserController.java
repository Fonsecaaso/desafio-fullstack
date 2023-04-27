package com.hubla.DesafioBack.controller;

import com.hubla.DesafioBack.entity.dto.UserDTO;
import com.hubla.DesafioBack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<UserDTO> getAll(){
        return  userService.getAll();
    }
}
