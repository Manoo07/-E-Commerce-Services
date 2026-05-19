package com.ecommerce.amazon.auth.controller;

import com.ecommerce.amazon.auth.dto.AuthResponse;
import com.ecommerce.amazon.auth.dto.LoginRequest;
import com.ecommerce.amazon.auth.dto.RegisterRequest;
import com.ecommerce.amazon.auth.service.AuthService;
import com.ecommerce.amazon.common.dto.ApiResponse;
import com.ecommerce.amazon.common.util.ResponseBuilder;
import com.ecommerce.amazon.user.dto.UserResponse;
import com.ecommerce.amazon.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest request){
        UserResponse userResponse = userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ResponseBuilder.success(
                                "User registered successfully",
                                userResponse
                        )
                );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request){
        AuthResponse authResponse = authService.login(request);

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        "Login sucessful",
                        authResponse
                )
        );
    }
}