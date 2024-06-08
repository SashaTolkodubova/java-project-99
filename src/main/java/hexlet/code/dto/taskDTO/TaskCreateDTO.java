package hexlet.code.dto.taskDTO;

import hexlet.code.model.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskCreateDTO {

    private int index;
    @NotNull
    private String title;

    private String content;
    @NotNull
    private String status;
    private Long assignee_id;
}
