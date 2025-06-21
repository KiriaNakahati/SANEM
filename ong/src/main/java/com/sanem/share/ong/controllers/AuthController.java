package com.sanem.share.ong.controllers;

import com.sanem.share.ong.dtos.auth.*;
import com.sanem.share.ong.infra.security.JwtUtils;
import com.sanem.share.ong.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtils jwtUtils;

    public AuthController(UserService userService, JwtUtils jwtUtils) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody @Valid AuthenticationDTO dto) throws AuthenticationException {
        var token = userService.login(dto);
        return ResponseEntity.ok(new ResponseDTO(token));
    }

    @GetMapping("/login")
    public ResponseEntity<ResponseDTO> login() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<ResponseDTO> register(@RequestBody @Valid RegisterDTO dto) {
        userService.Create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<ResponseDTO> Update(@RequestBody @Valid UpdateDTO dto, @RequestParam UUID id) {
        userService.Update(dto, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> delete(@RequestParam UUID id) {
        userService.Delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<ResponseDTO> confirmAccount(@RequestParam("token") String token) {
        userService.confirmAccount(token);
        return ResponseEntity.ok(new ResponseDTO(token));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestBody ResetPasswordDTO dto) {
        if (userService.resetPassword(token, dto)) {
            System.out.println("Token recebido: " + token);
            System.out.println("DTO recebido: " + dto);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Reset link sent successfully");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token) {
        if (jwtUtils.boolValidate(token)) {
            return ResponseEntity.ok(new ResponseDTO(token));
        }
        return ResponseEntity.badRequest().build();
    }

}
