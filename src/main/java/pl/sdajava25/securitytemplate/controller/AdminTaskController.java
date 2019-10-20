package pl.sdajava25.securitytemplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sdajava25.securitytemplate.model.TodoTask;
import pl.sdajava25.securitytemplate.service.AccountService;
import pl.sdajava25.securitytemplate.service.TodoTaskService;

import java.util.Set;

@Controller
@RequestMapping(path = "/admin/task/")
@PreAuthorize(value = "hasRole('ADMIN')")
public class AdminTaskController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private TodoTaskService todoTaskService;

    @GetMapping("/list")
    public String getTaskListForm(Model model) {
        model.addAttribute("userlist", accountService.getAll());

        return "user-chooser";
    }

    @PostMapping("/list")
    public String getTasksOfUser(Model model, String chosenUsername) {
        Set<TodoTask> taskSet = todoTaskService.getAllCurrent(chosenUsername);
        taskSet.addAll(todoTaskService.getAllArchived(chosenUsername));

        model.addAttribute("tasks", taskSet);

        return "task-list";
    }
}
