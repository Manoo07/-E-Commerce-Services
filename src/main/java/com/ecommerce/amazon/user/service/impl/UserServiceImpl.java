package com.ecommerce.amazon.user.service.impl;

import com.ecommerce.amazon.auth.dto.RegisterRequest;
import com.ecommerce.amazon.common.exception.BadRequestException;
import com.ecommerce.amazon.user.dto.UserResponse;
import com.ecommerce.amazon.user.entity.Role;
import com.ecommerce.amazon.user.entity.User;
import com.ecommerce.amazon.user.mapper.UserMapper;
import com.ecommerce.amazon.user.repository.UserRepository;
import com.ecommerce.amazon.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new BadRequestException("Email already exists");
        }

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(Role.CUSTOMER);

        User savedUser = userRepository.save(user);

        log.info(
                "User registered successfully with email={}",
                savedUser.getEmail()
        );

        return UserMapper.toResponse(savedUser);
    }
}
