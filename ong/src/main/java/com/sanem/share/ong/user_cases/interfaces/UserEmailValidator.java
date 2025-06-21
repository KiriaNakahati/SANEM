package com.sanem.share.ong.user_cases.interfaces;


import com.sanem.share.ong.dtos.auth.RegisterDTO;

public interface UserEmailValidator {
    void validate(RegisterDTO registerDTO);
}
