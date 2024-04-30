package hexlet.code.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    @GetMapping(path = "")
    public ResponseEntity<String> index() {
        return ResponseEntity
                .ok()
                .body("Hello");
    }

    @GetMapping(path = "/welcome")
    public ResponseEntity<String> getWelcomeString(){
        return ResponseEntity
                .ok()
                .body("Welcome to Spring");
    }
}
