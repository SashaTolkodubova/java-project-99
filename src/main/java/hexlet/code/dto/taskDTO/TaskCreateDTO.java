package hexlet.code.dto.taskDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TaskCreateDTO {

    private int index;
    @NotNull
    private String title;

    private String content;
    @NotNull
    private String status;

    @JsonProperty("assignee_id")
    private Long assigneeId;

}