package com.sanem.share.ong.user_cases.validations.user;

import com.sanem.share.ong.dtos.auth.RegisterDTO;
import com.sanem.share.ong.infra.exception_handler.exceptions.EmailAlreadyExistsValidator;
import com.sanem.share.ong.repositories.UserRepository;
import com.sanem.share.ong.user_cases.interfaces.UserEmailValidator;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class EmailAlreadyExists implements UserEmailValidator {
    private final UserRepository userRepository;

    public EmailAlreadyExists(@Lazy UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void validate(RegisterDTO registerDTO) {
        var user = userRepository.findByEmail(registerDTO.email());

        if (user.isPresent() && user.get().getEmail().equalsIgnoreCase(registerDTO.email())) {
            throw new EmailAlreadyExistsValidator("Email Already exists");
        }

    }
}
