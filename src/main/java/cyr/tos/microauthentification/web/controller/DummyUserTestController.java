package cyr.tos.microauthentification.web.controller;

import cyr.tos.microauthentification.web.dao.UserEntityRepository;
import cyr.tos.microauthentification.web.model.UserEntity;
import cyr.tos.microauthentification.web.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/")
public class DummyUserTestController {

    @Autowired
    UserEntityRepository userEntityRepository;

    @GetMapping("users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userEntityRepository.findAll());
    }
}
