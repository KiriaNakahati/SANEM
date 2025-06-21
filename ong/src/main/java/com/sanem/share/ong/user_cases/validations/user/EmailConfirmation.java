package com.sanem.share.ong.user_cases.validations.user;

import com.sanem.share.ong.infra.exception_handler.exceptions.EmailIsntConfirmed;
import com.sanem.share.ong.models.User;
import com.sanem.share.ong.services.UserService;
import com.sanem.share.ong.user_cases.interfaces.UserDataValidator;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EmailConfirmation implements UserDataValidator {

    private final UserService userService;

    public EmailConfirmation(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public void validate(UUID userId) {
        User user = userService.findUserById(userId);
        if (!user.isEmailConfirmed()) {
            throw new EmailIsntConfirmed("Email confirmation is invalid");
        }
    }
}
