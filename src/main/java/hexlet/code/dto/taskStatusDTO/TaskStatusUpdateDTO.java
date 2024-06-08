package hexlet.code.dto.taskStatusDTO;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;


@Getter
@Setter
public class TaskStatusUpdateDTO {
    private JsonNullable<Long> id;
    private JsonNullable<String> name;
    private JsonNullable<String> slug;
}
