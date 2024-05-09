package hexlet.code.service;

import hexlet.code.dto.taskStatusDTO.TaskStatusCreateDTO;
import hexlet.code.dto.taskStatusDTO.TaskStatusDTO;
import hexlet.code.dto.taskStatusDTO.TaskStatusUpdateDTO;
import hexlet.code.exeption.ResourceNotFoundException;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.repository.TaskStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskStatusService {
    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private TaskStatusMapper taskStatusMapper;

    public List<TaskStatusDTO> getAll() {
        var taskStatuses = taskStatusRepository.findAll();
        var result = taskStatuses.stream()
                .map(taskStatusMapper::map)
                .toList();
        return result;
    }

    public TaskStatusDTO create(TaskStatusCreateDTO taskStatusCreateDTO) {
        var taskStatus = taskStatusMapper.map(taskStatusCreateDTO);
        taskStatusRepository.save(taskStatus);
        var taskStatusDto = taskStatusMapper.map(taskStatus);
        return taskStatusDto;
    }

    public TaskStatusDTO findById(Long id) {
        var taskStatus = taskStatusRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TaskStatus with id " + id + " not found"));
        var taskStatusDto = taskStatusMapper.map(taskStatus);
        return taskStatusDto;
    }

    public TaskStatusDTO findBySlug(String slug) {
        var taskStatus = taskStatusRepository
                .findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("TaskStatus with slug " + slug + " not found"));
        var taskStatusDto = taskStatusMapper.map(taskStatus);
        return taskStatusDto;
    }

    public TaskStatusDTO update(TaskStatusUpdateDTO taskStatusUpdateDTO, Long id) {
        var taskStatus = taskStatusRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TaskStatus with id " + id + " not found"));
        taskStatusMapper.update(taskStatusUpdateDTO, taskStatus);
        taskStatusRepository.save(taskStatus);
        var taskStatusDto = taskStatusMapper.map(taskStatus);
        return taskStatusDto;
    }

    public void delete(long id) {
        taskStatusRepository.deleteById(id);
    }
}
