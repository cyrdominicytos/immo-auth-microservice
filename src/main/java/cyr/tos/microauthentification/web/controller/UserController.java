package cyr.tos.microauthentification.web.controller;

import cyr.tos.microauthentification.web.dao.UserEntityRepository;
import cyr.tos.microauthentification.web.model.UserEntity;
import cyr.tos.microauthentification.web.service.AuthService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/")
public class UserController {

    @Autowired
    AuthService authService;
    @Autowired
    UserEntityRepository userEntityRepository;

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody UserEntity user) {
        return ResponseEntity.ok( authService.register(user));
    }

    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.ok(authService.login(username, password));
    }

    @GetMapping("users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userEntityRepository.findAll());
    }
}
