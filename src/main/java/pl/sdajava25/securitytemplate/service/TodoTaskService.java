package pl.sdajava25.securitytemplate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sdajava25.securitytemplate.model.Account;
import pl.sdajava25.securitytemplate.model.TaskStatus;
import pl.sdajava25.securitytemplate.model.TodoTask;
import pl.sdajava25.securitytemplate.repository.AccountRepository;
import pl.sdajava25.securitytemplate.repository.TodoTaskRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TodoTaskService {
    @Autowired
    private TodoTaskRepository todoTaskRepository;
    @Autowired
    private AccountRepository accountRepository;

    public void addTask(TodoTask task, Principal principal) {
        Optional<Account> account = accountRepository.findByUsername(principal.getName());
        if (account.isPresent()) {
            Account userAccount = account.get();

            task.setAccount(userAccount);
            task.setStatus(TaskStatus.TODO);

            todoTaskRepository.save(task);
        }
    }

    public Set<TodoTask> getAllCurrent(String username) {
        Optional<Account> account = accountRepository.findByUsername(username);
        if (account.isPresent()) {
            Account userAccount = account.get();
            return userAccount
                    .getTasks()
                    .stream()
                    .filter(task -> task.getStatus() != TaskStatus.ARCHIVED)
                    .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    public Set<TodoTask> getAllArchived(String username) {
        Optional<Account> account = accountRepository.findByUsername(username);
        if (account.isPresent()) {
            Account userAccount = account.get();
            return userAccount
                    .getTasks()
                    .stream()
                    .filter(task -> task.getStatus() == TaskStatus.ARCHIVED)
                    .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    public void setDone(Long id, String username) {
        if (!userIsOwnerOf(username, id)) {
            return;
        }

        Optional<TodoTask> task = todoTaskRepository.findById(id);
        if (task.isPresent() && task.get().getStatus() != TaskStatus.ARCHIVED) {
            TodoTask todoTask = task.get();
            todoTask.setStatus(TaskStatus.DONE);
            todoTask.setDateFinished(LocalDateTime.now());

            todoTaskRepository.save(todoTask);
        }
    }

    public void setArchive(Long id, String username) {
        if (!userIsOwnerOf(username, id)) {
            return;
        }

        Optional<TodoTask> task = todoTaskRepository.findById(id);
        if (task.isPresent() && task.get().getStatus() == TaskStatus.DONE) {
            TodoTask todoTask = task.get();
            todoTask.setStatus(TaskStatus.ARCHIVED);

            todoTaskRepository.save(todoTask);
        }
    }

    public void setTodo(Long id, String username) {
        if (!userIsOwnerOf(username, id)) {
            return;
        }

        Optional<TodoTask> task = todoTaskRepository.findById(id);
        if (task.isPresent() && task.get().getStatus() == TaskStatus.DONE) {
            TodoTask todoTask = task.get();
            todoTask.setStatus(TaskStatus.TODO);
            todoTask.setDateFinished(null);

            todoTaskRepository.save(todoTask);
        }
    }

    public boolean userIsOwnerOf(String username, Long taskId) {
        Optional<Account> account = accountRepository.findByUsername(username);
        if (account.isPresent()) {
            Account user = account.get();

            return user.getTasks()
                    .stream()
                    .anyMatch(task -> task.getId() == taskId);
        }
        return false;
    }
}
