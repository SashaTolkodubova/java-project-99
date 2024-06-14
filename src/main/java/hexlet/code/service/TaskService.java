package hexlet.code.service;

import hexlet.code.dto.taskDTO.TaskCreateDTO;
import hexlet.code.dto.taskDTO.TaskDTO;
import hexlet.code.dto.taskDTO.TaskParamsDTO;
import hexlet.code.dto.taskDTO.TaskUpdateDTO;
import hexlet.code.exeption.ResourceNotFoundException;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.model.Task;
import hexlet.code.repository.TasksRepository;
import hexlet.code.specification.TaskSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskSpecification taskSpecification;

    public List<TaskDTO> getAll(TaskParamsDTO params) {
        Specification<Task> specification = taskSpecification.build(params);
        List<Task> allTasksWithParams = tasksRepository.findAll(specification);
        return allTasksWithParams.stream()
                .map(taskMapper::map)
                .toList();
    }

    public TaskDTO create(TaskCreateDTO taskCreateDTO) {
        var task = taskMapper.map(taskCreateDTO);
        tasksRepository.save(task);
        var taskDTO = taskMapper.map(task);
        return taskDTO;
    }

    public TaskDTO findById(Long id) {
        var task = tasksRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        var taskDTO = taskMapper.map(task);
        return taskDTO;
    }

    public TaskDTO update(TaskUpdateDTO taskUpdateDTO, Long id) {
        var task = tasksRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        taskMapper.update(taskUpdateDTO, task);
        tasksRepository.save(task);
        var taskDTO = taskMapper.map(task);
        return taskDTO;
    }

    public void delete(Long id) {
        tasksRepository.deleteById(id);
    }
}
