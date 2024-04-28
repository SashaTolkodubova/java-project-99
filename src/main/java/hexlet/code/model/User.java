package hexlet.code.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@RequiredArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @CreatedDate
    private String createdAt;
    @LastModifiedDate
    private String updatedAt;
}
