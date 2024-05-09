package freezy.controllers;

import freezy.entities.Project;
import freezy.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable String id) {
        return projectService.getProjectById(id);
    }

    @PostMapping
    public void addProject(@RequestBody Project project) {
        projectService.saveProject(project);
    }

    @PutMapping("/{id}")
    public void updateProject(@PathVariable String id, @RequestBody Project project) {
        if (projectService.getProjectById(id) != null) {
            projectService.saveProject(project);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
    }
}
