package freezy.services;



import freezy.entities.ProjectPlan;
import freezy.repository.ProjectPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectPlanService {
    @Autowired
    private ProjectPlanRepository projectPlanRepository;

    public List<ProjectPlan> getAllProjectPlans() {
        return projectPlanRepository.findAll();
    }

    public ProjectPlan getProjectPlanById(String id) {
        return projectPlanRepository.findById(id).orElse(null);
    }

    public void saveProjectPlan(ProjectPlan projectPlan) {
        projectPlanRepository.save(projectPlan);
    }

    public void deleteProjectPlan(String id) {
        projectPlanRepository.deleteById(id);
    }
}

