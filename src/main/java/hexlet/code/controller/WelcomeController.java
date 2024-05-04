package hexlet.code.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @GetMapping(path = "/welcome")
    public ResponseEntity<String> getWelcomeString() {
        return ResponseEntity
                .ok()
                .body("Welcome to Spring");
    }
}
