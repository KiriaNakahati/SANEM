package com.sanem.share.ong.dtos.auth;

import com.sanem.share.ong.enums.User_Role;
import com.sanem.share.ong.models.User;

import java.util.UUID;

public record GetUsersDTO(
        UUID uuid,
        String firstName,
        String lastName,
        String email,
        User_Role role) {

    public GetUsersDTO(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUserRole());
    }
}
