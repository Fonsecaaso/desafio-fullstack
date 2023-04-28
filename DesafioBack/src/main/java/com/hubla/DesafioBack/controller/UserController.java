package com.hubla.DesafioBack.controller;

import com.hubla.DesafioBack.entity.dto.UserDTO;
import com.hubla.DesafioBack.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Download list with username and account balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users")})
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<UserDTO> getAll(){
        return  userService.getAll();
    }
}
