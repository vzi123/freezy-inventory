package freezy.services;



import freezy.entities.Quotation;
import freezy.entities.QuotationItems;
import freezy.repository.QuotationItemsRepository;
import freezy.repository.QuotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuotationItemsService {

    @Autowired
    private QuotationItemsRepository quotationItemsRepository;

    public List<QuotationItems> getAllQuotationItems() {
        return quotationItemsRepository.findAll();
    }

    public QuotationItems getQuotationItemById(String id) {
        return quotationItemsRepository.findById(id).orElse(null);
    }

    public void saveQuotationItems(QuotationItems quotationItems) {
        quotationItemsRepository.saveAndFlush(quotationItems);
    }

    public void deleteQuotationItem(String id) {
        quotationItemsRepository.deleteById(id);
    }

    public void deleteQuotationItems(List<QuotationItems> items){
        quotationItemsRepository.deleteAll(items);
    }
}
