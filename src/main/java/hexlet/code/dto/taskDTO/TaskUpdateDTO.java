package hexlet.code.dto.taskDTO;

import hexlet.code.model.User;
import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;


@Getter
@Setter
public class TaskUpdateDTO {

    private JsonNullable<Integer> index;

    private JsonNullable<String> title;

    private JsonNullable<String> content;

    private JsonNullable<String> status;

    private JsonNullable<User> assignee_id;
}
