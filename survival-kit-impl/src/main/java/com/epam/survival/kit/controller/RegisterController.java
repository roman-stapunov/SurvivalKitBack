package com.epam.survival.kit.controller;

import com.epam.survival.kit.controller.dto.RegisterResponse;
import com.epam.survival.kit.data.dao.UserRepository;
import com.epam.survival.kit.data.model.SurvivalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody SurvivalUser survivalUser) {
        String encodedPass = passwordEncoder.encode(survivalUser.getPassword());
        survivalUser.setPassword(encodedPass);
        SurvivalUser savedUser = userRepository.save(survivalUser);
        RegisterResponse response = new RegisterResponse(
                savedUser.getName(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getId()
        );
        return ResponseEntity.ok(response);
    }
}
