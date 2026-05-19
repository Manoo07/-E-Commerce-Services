package com.ecommerce.amazon.user.service;

import com.ecommerce.amazon.auth.dto.RegisterRequest;
import com.ecommerce.amazon.user.dto.UserResponse;

public interface UserService {

    UserResponse register(RegisterRequest request);
}
