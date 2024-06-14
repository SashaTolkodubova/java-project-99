package hexlet.code.controller.api;

import hexlet.code.dto.taskDTO.TaskCreateDTO;
import hexlet.code.dto.taskDTO.TaskDTO;
import hexlet.code.dto.taskDTO.TaskParamsDTO;
import hexlet.code.dto.taskDTO.TaskUpdateDTO;
import hexlet.code.service.TaskService;
import hexlet.code.specification.TaskSpecification;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tasks/")
public class TasksController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskSpecification taskSpecification;

    @GetMapping("")
    public ResponseEntity<List<TaskDTO>> index(TaskParamsDTO params) {
        var tasks = taskService.getAll(params);
        var result = ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(tasks.size()))
                .body(tasks);
        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long id) {
        var task = taskService.findById(id);
        return ResponseEntity.ok()
                .body(task);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO createTask(@Valid @RequestBody TaskCreateDTO taskCreateDTO) {
        return taskService.create(taskCreateDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO updateTask(@RequestBody TaskUpdateDTO taskUpdateDTO, @PathVariable Long id) {
        return taskService.update(taskUpdateDTO, id);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.delete(id);
    }
}
