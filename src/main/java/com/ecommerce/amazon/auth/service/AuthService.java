package com.ecommerce.amazon.auth.service;

import com.ecommerce.amazon.auth.dto.AuthResponse;
import com.ecommerce.amazon.auth.dto.LoginRequest;

public interface AuthService {

    AuthResponse login(LoginRequest request);
}
