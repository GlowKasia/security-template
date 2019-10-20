package pl.sdajava25.securitytemplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sdajava25.securitytemplate.model.TodoTask;
import pl.sdajava25.securitytemplate.service.TodoTaskService;

import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping(path = "/task/")
public class TaskController {
    @Autowired
    private TodoTaskService todoTaskService;

    @GetMapping("/add")
    public String addForm(Model model, TodoTask task) {
        model.addAttribute("taskobject", task);

        return "task-form";
    }

    @GetMapping("/list")
    public String taskList(Model model, Principal principal) {
        Set<TodoTask> taskSet = todoTaskService.getAllCurrent(principal.getName());
        model.addAttribute("tasks", taskSet);

        return "task-list";
    }

    @GetMapping("/list/archived")
    public String taskListArchived(Model model, Principal principal) {
        Set<TodoTask> taskSet = todoTaskService.getAllArchived(principal.getName());
        model.addAttribute("tasks", taskSet);

        return "task-list";
    }

    @PostMapping("/add")
    public String addForm(TodoTask task, Principal principal) {
        todoTaskService.addTask(task, principal);

        return "redirect:/task/list";
    }

    @GetMapping("/done/{id}")
    public String setDone(@PathVariable("id") Long id, Principal principal) {
        todoTaskService.setDone(id, principal.getName());

        return "redirect:/task/list";
    }

    @GetMapping("/todo/{id}")
    public String setTodo(@PathVariable("id") Long id, Principal principal) {
        todoTaskService.setTodo(id, principal.getName());

        return "redirect:/task/list";
    }

    @GetMapping("/archive/{id}")
    public String setArchive(@PathVariable("id") Long id, Principal principal) {
        todoTaskService.setArchive(id, principal.getName());

        return "redirect:/task/list";
    }

}
