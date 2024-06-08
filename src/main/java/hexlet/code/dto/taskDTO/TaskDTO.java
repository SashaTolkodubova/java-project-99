package hexlet.code.dto.taskDTO;

import hexlet.code.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskDTO {

    private Long id;

    private int index;

    private LocalDate createdAt;

    private String title;

    private String content;

    private String status;
    private User assignee_id;
}
