package pl.sdajava25.securitytemplate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sdajava25.securitytemplate.model.TodoTask;

public interface TodoTaskRepository extends JpaRepository<TodoTask, Long> {
}
