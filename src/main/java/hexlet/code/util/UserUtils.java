package hexlet.code.util;

import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {
    @Autowired
    private UserRepository userRepository;

    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        var email = authentication.getName();
        return userRepository.findByEmail(email).get();
    }

    public boolean isSelf(long id) {
        var userEmail = userRepository.findById(id).get().getEmail();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return userEmail.equals(authentication.getName());
    }
}
