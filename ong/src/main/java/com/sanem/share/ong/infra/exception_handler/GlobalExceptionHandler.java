package com.sanem.share.ong.infra.exception_handler;

import com.sanem.share.ong.infra.exception_handler.exceptions.EmailAlreadyExistsValidator;
import com.sanem.share.ong.infra.exception_handler.exceptions.EmailIsntConfirmed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistsValidator.class)
    public ResponseEntity<Map<String, String>> EmailAlreadyExists() {
        var body = ErrorMessage("The cpf is already registered");
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(EmailIsntConfirmed.class)
    public ResponseEntity<Map<String, String>> EmailIsntConfirmed() {
        var body = ErrorMessage("The cpf isnt confirmed");
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> userOrPasswordWrong() {
        var body = ErrorMessage("Login failed. Please check your cpf and password and try again.");
        return ResponseEntity.badRequest().body(body);
    }

    private Map<String, String> ErrorMessage(String errorMessage) {
        Map<String, String> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("error", errorMessage);
        return body;
    }



}