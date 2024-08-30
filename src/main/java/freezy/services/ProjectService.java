package freezy.services;


import freezy.entities.Project;
import freezy.repository.ProjectRepository;
import freezy.utils.FreazyConstants;
import freezy.utils.FreazyUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    UserService userService;

    @Autowired
    FreazyUtilsService freazyUtilsService;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(String id) {
        return projectRepository.findById(id).orElse(null);
    }

    public void saveProject(Project project) {
        project.setId(freazyUtilsService.generateId(FreazyConstants.PROJECT_ORDER_PREFIX));
        project.setCreatedAt(freazyUtilsService.generateDate());
        project.setCreatedBy(userService.getSuperUser());
        projectRepository.save(project);
    }

    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }
}