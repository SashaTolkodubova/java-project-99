package hexlet.code.mapper;

import hexlet.code.dto.UserCreateDTO;
import hexlet.code.dto.UserDTO;
import hexlet.code.dto.UserUpdateDTO;
import hexlet.code.model.User;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(
        uses = {JsonNullableMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;


    public abstract User map(UserCreateDTO dto);

    @Mapping(target = "password", ignore = true)
    public abstract UserDTO map(User model);

    public abstract void update(UserUpdateDTO dto, @MappingTarget User model);

    @BeforeMapping
    public void encryptPassword(UserCreateDTO createDTO) {
        String password = createDTO.getPassword();
        createDTO.setPassword(passwordEncoder.encode(password));
    }

//    @BeforeMapping
//    public void encryptPassword(UserUpdateDTO updateDTO) {
//        if (updateDTO.getPassword() != null) {
//            String password = updateDTO.getPassword().get();
//        }
//    }
}
