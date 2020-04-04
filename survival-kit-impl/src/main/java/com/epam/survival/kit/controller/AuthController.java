package com.epam.survival.kit.controller;

import com.epam.survival.kit.auth.JwtUserDetailsService;
import com.epam.survival.kit.auth.jwt.JwtTokenRequest;
import com.epam.survival.kit.auth.jwt.JwtTokenResponse;
import com.epam.survival.kit.auth.jwt.JwtTokenUtil;
import com.epam.survival.kit.auth.jwt.JwtUserDetails;
import com.epam.survival.kit.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "${jwt.url.token}")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public ResponseEntity<JwtTokenResponse> auth(@RequestBody JwtTokenRequest authRequest) throws AuthenticationException {
        authenticate(authRequest.getUsername(), authRequest.getPassword());

        final JwtUserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtTokenResponse(userDetails.getId(), userDetails.getRole(), token));
    }

    @PostMapping(value = "${jwt.url.refresh}")
    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    public void refreshAuth() {

    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    private void authenticate(@NotNull String username, @NotNull String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            log.warn("User disabled", e);
            throw new AuthenticationException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            log.warn("Bad credentials", e);
            throw new AuthenticationException("INVALID_CREDENTIALS", e);
        }
    }
}
