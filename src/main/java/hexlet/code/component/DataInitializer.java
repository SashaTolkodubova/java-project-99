package hexlet.code.component;

import hexlet.code.dto.labelDTO.LabelCreateDTO;
import hexlet.code.dto.taskStatusDTO.TaskStatusCreateDTO;
import hexlet.code.dto.userDTO.UserCreateDTO;
import hexlet.code.service.LabelService;
import hexlet.code.service.TaskStatusService;
import hexlet.code.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private final TaskStatusService taskStatusService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final LabelService labelService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        UserCreateDTO userData = new UserCreateDTO();
        userData.setFirstName("admin");
        userData.setLastName("admin");
        userData.setEmail("hexlet@example.com");
        userData.setPassword("qwerty");
        userService.create(userData);

        TaskStatusCreateDTO draft = new TaskStatusCreateDTO();
        TaskStatusCreateDTO toReview = new TaskStatusCreateDTO();
        TaskStatusCreateDTO toBeFixed = new TaskStatusCreateDTO();
        TaskStatusCreateDTO toPublish = new TaskStatusCreateDTO();
        TaskStatusCreateDTO published = new TaskStatusCreateDTO();

        draft.setName("draft");
        draft.setSlug("draft");

        toReview.setName("to review");
        toReview.setSlug("to_review");

        toBeFixed.setName("to be fixed");
        toBeFixed.setSlug("to_be_fixed");

        toPublish.setName("to publish");
        toPublish.setSlug("to_publish");

        published.setName("published");
        published.setSlug("published");

        taskStatusService.create(draft);
        taskStatusService.create(toReview);
        taskStatusService.create(toBeFixed);
        taskStatusService.create(toPublish);
        taskStatusService.create(published);

        LabelCreateDTO feature = new LabelCreateDTO();
        LabelCreateDTO bug = new LabelCreateDTO();

        feature.setName("feature");
        bug.setName("bug");
        labelService.create(bug);
        labelService.create(feature);
    }
}
