package hexlet.code.mapper;

import hexlet.code.model.BaseEntity;
import hexlet.code.model.Label;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;
import jakarta.persistence.EntityManager;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public abstract class ReferenceMapper {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private LabelRepository labelRepository;

    public <T extends BaseEntity> T toEntity(Long id, @TargetType Class<T> entityClass) {
        return id != null ? entityManager.find(entityClass, id) : null;
    }

    public TaskStatus toEntity(String slug) {
        TaskStatus status = taskStatusRepository.findBySlug(slug).orElseThrow();
        return slug != null ? status : null;
    }

    public Set<Label> idToLabel(Set<Long> taskLabelIds) {
        Set<Label> labels = labelRepository.findByIdIn(taskLabelIds);
        Set<Label> odjects = !labels.isEmpty() ? labels : Collections.emptySet();
        return odjects;
    }

    public Set<Long> labelToId(Set<Label> labels) {
        Set<Long> taskLabelIds = new LinkedHashSet<>();
        for (Label label : labels) {
            taskLabelIds.add(label.getId());
        }
        return taskLabelIds;
    }
}
