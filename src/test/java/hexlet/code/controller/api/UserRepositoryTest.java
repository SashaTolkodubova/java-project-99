package hexlet.code.controller.api;

import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userRepositorySaveTest() {
        User user = new User();
        user.setFirstName("User1Name");
        user.setLastName("User1LastName");
        user.setEmail("user1Email@ff .com");

        User savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void userRepositoryFindAllTest() {
        User user1 = new User();
        user1.setFirstName("User1Name");
        user1.setLastName("User1LastName");
        user1.setEmail("user1Email@ff .com");

        User user2 = new User();
        user2.setFirstName("User2Name");
        user2.setLastName("User2LastName");
        user2.setEmail("user2Email@ff .com");

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> usersList = userRepository.findAll();

        Assertions.assertThat(usersList).isNotNull();
        Assertions.assertThat(usersList.size()).isEqualTo(2);
    }
}