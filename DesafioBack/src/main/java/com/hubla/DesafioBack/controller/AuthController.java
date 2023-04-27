package com.hubla.DesafioBack.controller;

import com.demo.Sistema.de.Monitoramento.V1.model.dto.AuthResponseDTO;
import com.demo.Sistema.de.Monitoramento.V1.model.dto.LoginDTO;
import com.demo.Sistema.de.Monitoramento.V1.model.dto.RegisterDTO;
import com.demo.Sistema.de.Monitoramento.V1.model.entity.Role;
import com.demo.Sistema.de.Monitoramento.V1.model.entity.UserEntity;
import com.demo.Sistema.de.Monitoramento.V1.repository.RoleRepository;
import com.demo.Sistema.de.Monitoramento.V1.repository.UserRepository;
import com.demo.Sistema.de.Monitoramento.V1.repository.UserRepositoryJPA;
import com.demo.Sistema.de.Monitoramento.V1.security.JWTGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserRepositoryJPA userRepositoryJPA;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGenerator jwtGenerator;
    private UserRepository userRepository;

    @Value("${application.header-ip-candidates}")
    private String[] headerCandidates;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepositoryJPA userRepositoryJPA, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepositoryJPA = userRepositoryJPA;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.userRepository = userRepository;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        for (String header: headerCandidates) {
            String ipList = request.getHeader(header);
            if (ipList != null && ipList.length() != 0 && !"unknown".equalsIgnoreCase(ipList)) {
                break;
            }
        }

        userRepository.updateIp(loginDto.getUsername(),request.getRemoteAddr());

        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDto, @RequestParam String role) {
        if (userRepositoryJPA.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));

        Role roles = roleRepository.findByName(role).get();
        user.setRoles(Collections.singletonList(roles));

        userRepositoryJPA.save(user);

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }
}
