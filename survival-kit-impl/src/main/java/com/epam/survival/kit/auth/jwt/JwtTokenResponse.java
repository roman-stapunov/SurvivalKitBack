package com.epam.survival.kit.auth.jwt;

import com.epam.survival.kit.data.model.UserRole;
import lombok.Data;

@Data
public class JwtTokenResponse {

    private long userId;
    private UserRole userRole;
    private String token;

    public JwtTokenResponse(long userId, UserRole userRole, String token) {
        this.userId = userId;
        this.userRole = userRole;
        this.token = token;
    }
}
