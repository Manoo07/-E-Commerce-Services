package com.ecommerce.amazon.auth.service;

import com.ecommerce.amazon.auth.dto.AuthResponse;
import com.ecommerce.amazon.auth.dto.LoginRequest;
import com.ecommerce.amazon.common.exception.BadRequestException;
import com.ecommerce.amazon.security.jwt.JwtTokenProvider;
import com.ecommerce.amazon.user.entity.User;
import com.ecommerce.amazon.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthResponse login(LoginRequest request){
        User user = userRepository.findByEmail(
                request.getEmail()
        ).orElseThrow(()->{
            return new BadRequestException(
                    "Invalid email and password"
            );
        });

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if(!matches){
            throw new BadRequestException(
                    "Invalid Email or Password"
            );
        }

        String token = jwtTokenProvider.generateToken(user.getEmail());

        log.info(
                "User logged in successfully email : {}",
                user.getEmail()
        );

        return AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .build();
    }
}
