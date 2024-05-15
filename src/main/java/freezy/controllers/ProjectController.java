package freezy.controllers;

import freezy.entities.Project;
import freezy.schedule.StockAlertSchedule;
import freezy.services.ProjectService;
import freezy.utils.StockAlertEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private StockAlertEmailService stockAlertEmailService;


    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Project> getAllProjects() throws Exception {
        //stockAlertEmailService.checkAndSendEmail();
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable String id) {
        return projectService.getProjectById(id);
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public Project addProject(@RequestBody Project project) {
        projectService.saveProject(project);
        return projectService.getProjectById(project.getId());
    }

    @PutMapping("/{id}")
    public Project updateProject(@PathVariable String id, @RequestBody Project project) {
        if (projectService.getProjectById(id) != null) {
            projectService.saveProject(project);
        }
        return projectService.getProjectById(project.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
    }
}
