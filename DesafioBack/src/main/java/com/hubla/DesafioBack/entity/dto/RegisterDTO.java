package com.hubla.DesafioBack.entity.dto;

import lombok.Data;

@Data
public class RegisterDTO {
    private String email;
    private String username;
    private String password;
}
