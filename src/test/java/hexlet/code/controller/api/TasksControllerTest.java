package hexlet.code.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.taskDTO.TaskCreateDTO;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.TasksRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.util.ModelGenerator;
import jakarta.transaction.Transactional;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TasksControllerTest {

    private SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor token;
    private Task testTask;
    private TaskStatus testTaskStatus;
    private User testUser;
    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        token = jwt().jwt(builder -> builder.subject("hexlet@example.com"));
        testTaskStatus = Instancio.of(modelGenerator.getTaskStatusModel()).create();
        taskStatusRepository.save(testTaskStatus);

        testTask = Instancio.of(modelGenerator.getTaskModel())
                .set(Select.field(Task::getAssignee), null)
                .create();
        testTask.setTaskStatus(testTaskStatus);
        tasksRepository.save(testTask);
        testUser = Instancio.of(modelGenerator.getUserModel()).create();
        userRepository.save(testUser);
    }

    @AfterEach
    public void afterEach() {
        tasksRepository.delete(testTask);
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(
                get("/api/tasks/")
                        .with(token)
        ).andExpect(status().isOk());
    }

    @Test
    public void testShow() throws Exception {
        mockMvc.perform(
                get("/api/tasks/{id}", testTask.getId())
                        .with(token)
        ).andExpect(status().isOk());


        Task task = tasksRepository.findById(testTask.getId()).get();
        assertThat(task.getAssignee()).isEqualTo(testTask.getAssignee());
        assertThat(task.getName()).isEqualTo(testTask.getName());
    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
        TaskCreateDTO taskToSave = new TaskCreateDTO();
        taskToSave.setTitle("TestName");
        taskToSave.setStatus(testTaskStatus.getSlug());
        taskToSave.setContent("TestContent");
        taskToSave.setIndex(100);
        taskToSave.setAssigneeId(testUser.getId());

        mockMvc.perform(
                post("/api/tasks/")
                        .with(token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskToSave))
        ).andExpect(status().isCreated());

        Task savedTask = tasksRepository.findByName(taskToSave.getTitle()).get();

        assertNotNull(savedTask);
        assertThat(savedTask.getName()).isEqualTo(taskToSave.getTitle());
        assertThat(savedTask.getTaskStatus().getSlug()).isEqualTo(taskToSave.getStatus());
    }

    @Test
    public void testUpdate() throws Exception {
        var data = new HashMap<>();
        data.put("title", "newTaskName");

        var request = put("/api/tasks/" + testTask.getId())
                .with(jwt().jwt(builder -> builder.subject(testUser.getEmail())))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data));

        mockMvc.perform(request)
                .andExpect(status().isOk());

        var task = tasksRepository.findById(testTask.getId()).get();
        assertThat(task.getName()).isEqualTo("newTaskName");
    }
}