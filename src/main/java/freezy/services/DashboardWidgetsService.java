package freezy.services;

import freezy.entities.DashBoardWidgets;
import freezy.repository.DashboardWidgetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardWidgetsService {

    @Autowired
    DashboardWidgetsRepository dashboardWidgetsRepository;

    public List<DashBoardWidgets> getAllWidgets(){
        return dashboardWidgetsRepository.findAllByOrderBySequenceAsc();
    }
}
