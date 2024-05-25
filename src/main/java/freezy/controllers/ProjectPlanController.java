package freezy.controllers;


import freezy.entities.ProjectPlan;
import freezy.services.ProjectPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project-plans")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProjectPlanController {
    @Autowired
    private ProjectPlanService projectPlanService;

    @GetMapping
    public List<ProjectPlan> getAllProjectPlans() {
        return projectPlanService.getAllProjectPlans();
    }

    @GetMapping("/{id}")
    public ProjectPlan getProjectPlanById(@PathVariable String id) {
        return projectPlanService.getProjectPlanById(id);
    }

    @PostMapping
    public void addProjectPlan(@RequestBody ProjectPlan projectPlan) {
        projectPlanService.saveProjectPlan(projectPlan);
    }

    @PutMapping("/{id}")
    public void updateProjectPlan(@PathVariable String id, @RequestBody ProjectPlan projectPlan) {
        if (projectPlanService.getProjectPlanById(id) != null) {
            projectPlanService.saveProjectPlan(projectPlan);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProjectPlan(@PathVariable String id) {
        projectPlanService.deleteProjectPlan(id);
    }
}
