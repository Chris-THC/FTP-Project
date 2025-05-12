package com.ftp.api.service;

import com.ftp.api.dto.AuthResponse;
import com.ftp.api.form.LoginRequest;
import com.ftp.api.repositori.JwtAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:ValidationsMessages.properties")
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtAuthRepository userRepository;
    private final JwtService jwtService;

    @Value("${not.found}")
    private String notFound;

    public AuthResponse login(LoginRequest request) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getNumControl(), request.getPassword()));
        validateUserIfExists(request.getNumControl());
        UserDetails user = userRepository.findByNumControl(request.getNumControl());
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    private void validateUserIfExists(final String controlNumber) throws Exception {
        if (userRepository.findByNumControl(controlNumber) == null) {
            throw new Exception(notFound);
        }
    }
}
