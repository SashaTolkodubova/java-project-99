package hexlet.code.dto.taskStatusDTO;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TaskStatusUpdateDTO {
    private Long id;
    private String name;
    private String slug;
}
