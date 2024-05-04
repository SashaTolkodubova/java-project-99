package hexlet.code.util;

import hexlet.code.model.User;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ModelGenerator {
    private Model<User> userModel;

    @Autowired
    private Faker faker;

    @PostConstruct
    private void init() {
        userModel = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .supply(Select.field(User::getFirstName), () -> faker.gameOfThrones().character())
                .supply(Select.field(User::getLastName), () -> faker.gameOfThrones().character())
                .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
                .supply(Select.field(User::getPassword), () -> faker.gameOfThrones().character())
                .toModel();
    }

}
