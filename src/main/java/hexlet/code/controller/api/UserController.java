package hexlet.code.controller.api;

import hexlet.code.dto.UserCreateDTO;
import hexlet.code.dto.UserDTO;
import hexlet.code.dto.UserUpdateDTO;
import hexlet.code.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> index() {
     var users = userService.getAll();
     var result = ResponseEntity.ok()
             .header("X-Total-Count", String.valueOf(users.size()))
             .body(users);
     return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        var user = userService.findById(id);
        return ResponseEntity.ok()
                .body(user);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@Valid @RequestBody UserCreateDTO userData) {
        return userService.create(userData);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@userUtils.isSelf(#id)")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@RequestBody UserUpdateDTO userUpdateDTO, @PathVariable Long id) {
        return userService.update(userUpdateDTO, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@userUtils.isSelf(#id)")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
