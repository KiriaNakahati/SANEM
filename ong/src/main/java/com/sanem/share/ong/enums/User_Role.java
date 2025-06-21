package com.sanem.share.ong.enums;

import lombok.Getter;

@Getter
public enum User_Role {
    ADMIN("admin"), USER("user");


    private final String role;

    User_Role(String role) {
        this.role = role;
    }
}
