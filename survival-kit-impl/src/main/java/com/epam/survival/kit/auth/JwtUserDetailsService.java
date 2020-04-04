package com.epam.survival.kit.auth;

import com.epam.survival.kit.auth.jwt.JwtUserDetails;
import com.epam.survival.kit.data.dao.UserRepository;
import com.epam.survival.kit.data.model.SurvivalUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public JwtUserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        SurvivalUser user = userRepository.findByUsernameIgnoreCase(usernameOrEmail);
        if (user == null) {
            user = userRepository.findByEmailIgnoreCase(usernameOrEmail);
        }
        return new JwtUserDetails(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    }
}
