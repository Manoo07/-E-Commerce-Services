package com.ecommerce.amazon.user.mapper;

import com.ecommerce.amazon.user.dto.UserResponse;
import com.ecommerce.amazon.user.entity.User;

public class UserMapper {

    private UserMapper(){}

    public static UserResponse toResponse(User user){

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();

    }
}
