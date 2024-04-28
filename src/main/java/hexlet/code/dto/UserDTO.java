package hexlet.code.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String createdAt;
    private String updatedAt;
}
