package com.epam.survival.kit.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtTokenRequest {

    private String username;
    private String password;
}
