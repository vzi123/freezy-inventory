package freezy.services;



import freezy.entities.Category;
import freezy.entities.StockAlerts;
import freezy.repository.CategoryRepository;
import freezy.repository.StockAlertsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockAlertService {

    @Autowired
    private StockAlertsRepository stockAlertsRepository;

    public List<StockAlerts> getAllStockAlerts() {
        return stockAlertsRepository.findAll((Sort.by(Sort.Direction.DESC, "createdAt")));
    }
}
