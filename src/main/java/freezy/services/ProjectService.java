package freezy.services;


import freezy.entities.Project;
import freezy.repository.ProjectRepository;
import freezy.utils.Constants;
import freezy.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    UserService userService;

    @Autowired
    UtilsService utilsService;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(String id) {
        return projectRepository.findById(id).orElse(null);
    }

    public void saveProject(Project project) {
        project.setId(utilsService.generateId(Constants.PROJECT_ORDER_PREFIX));
        project.setCreatedAt(utilsService.generateDateFormat());
        project.setCreatedBy(userService.getSuperUser());
        projectRepository.save(project);
    }

    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }
}