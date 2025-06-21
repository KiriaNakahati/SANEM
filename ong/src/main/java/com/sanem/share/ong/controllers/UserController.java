package com.sanem.share.ong.controllers;

import com.sanem.share.ong.dtos.auth.GetUsersDTO;
import com.sanem.share.ong.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@CrossOrigin(origins = "http://127.0.0.1:5500") // ← libera CORS para o seu front
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Stream<GetUsersDTO>> getUsers() {
        var users = userService.findAll();
        return ResponseEntity.ok(users.stream().map(GetUsersDTO::new));
    }

}
