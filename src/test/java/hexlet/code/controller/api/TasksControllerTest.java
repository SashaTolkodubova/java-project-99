package hexlet.code.controller.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.taskDTO.TaskCreateDTO;
import hexlet.code.dto.taskDTO.TaskDTO;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.LabelRepository;
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
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Label testLabel;
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

    @Autowired
    private LabelRepository labelRepository;

    @BeforeEach
    public void beforeEach() {
        token = jwt().jwt(builder -> builder.subject("hexlet@example.com"));
        testTaskStatus = Instancio.of(modelGenerator.getTaskStatusModel()).create();
        taskStatusRepository.save(testTaskStatus);
        testLabel = new Label();
        testLabel.setName("testLabelName");
        labelRepository.save(testLabel);
        Set<Label> testLabelSet = new HashSet<>();
        testLabelSet.add(testLabel);

        testTask = Instancio.of(modelGenerator.getTaskModel())
                .set(Select.field(Task::getAssignee), null)
                .create();
        testTask.setTaskStatus(testTaskStatus);
        testTask.setLabelList(testLabelSet);
        tasksRepository.save(testTask);
        testUser = Instancio.of(modelGenerator.getUserModel()).create();
        userRepository.save(testUser);
    }

    @AfterEach
    public void afterEach() {

        tasksRepository.delete(testTask);
//        taskStatusRepository.delete(testTaskStatus);
//        userRepository.delete(testUser);
        labelRepository.delete(testLabel);
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(
                get("/api/tasks/")
                        .with(token)
        ).andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testGetTaskWithParams() throws Exception {
        Task task1 = Instancio.of(modelGenerator.getTaskModel()).create();
        task1.setTaskStatus(testTaskStatus);
        task1.setAssignee(testUser);
        Set<Label> labelSet = new HashSet<>();
        labelSet.add(testLabel);
        task1.setLabelList(labelSet);
        tasksRepository.save(task1);

        String queryString1 = "?titleConst=" + task1.getName()
                + "&assigneeId=" + task1.getAssignee().getId()
                + "&status=" + task1.getTaskStatus().getSlug();

        MvcResult result = mockMvc.perform(
                        get("/api/tasks/" + "?assigneeId=" + testUser.getId())
                                .with(token))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        List<TaskDTO> taskList = objectMapper.readValue(content, new TypeReference<>() {
        });
        assertThat(taskList).hasSize(1);
        assertThat(taskList.get(0).getId()).isEqualTo(task1.getId());
    }

    @Test
    public void testGetTask() throws Exception {
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
