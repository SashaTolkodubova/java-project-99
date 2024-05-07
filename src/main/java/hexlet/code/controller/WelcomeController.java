package hexlet.code.controller;

import hexlet.code.dto.UserDTO;
import hexlet.code.repository.UserRepository;
import hexlet.code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WelcomeController {

    @GetMapping(path = "/welcome")
    public ResponseEntity<String> getWelcomeString() {
        return ResponseEntity
                .ok()
                .body("Welcome to Spring");
    }
}
