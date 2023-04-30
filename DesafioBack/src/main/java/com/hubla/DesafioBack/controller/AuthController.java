package com.hubla.DesafioBack.controller;

import com.hubla.DesafioBack.entity.Role;
import com.hubla.DesafioBack.entity.UserEntity;
import com.hubla.DesafioBack.entity.dto.LoginDTO;
import com.hubla.DesafioBack.repository.RoleRepository;
import com.hubla.DesafioBack.repository.UserRepository;
import com.hubla.DesafioBack.security.JWTGenerator;
import com.hubla.DesafioBack.entity.dto.AuthResponseDTO;
import com.hubla.DesafioBack.entity.dto.RegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepositoryJPA;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JWTGenerator jwtGenerator;
    @Autowired
    UserRepository userRepository;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);

        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDto, @RequestParam String role) {
        if (userRepositoryJPA.findByName(registerDto.getUsername()).isPresent()) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        Role roles = roleRepository.findByName(role).get();
        UserEntity user = UserEntity.builder()
                .name(registerDto.getUsername())
                .email(registerDto.getEmail())
                .isProducer(role.equals("PRODUCER"))
                .balance(0.0)
                .password(passwordEncoder.encode((registerDto.getPassword())))
                .roles(Collections.singletonList(roles))
                .build();

        userRepositoryJPA.save(user);

        return new ResponseEntity<>("User registered!", HttpStatus.OK);
    }
}
