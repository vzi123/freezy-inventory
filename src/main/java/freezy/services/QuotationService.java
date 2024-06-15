package freezy.services;



import freezy.dto.QuotationDTO;
import freezy.dto.QuotationItemsDTO;
import freezy.entities.*;
import freezy.entities.Product;
import freezy.events.QuotationCreatedPublisher;
import freezy.repository.CategoryRepository;
import freezy.repository.ProductRepository;
import freezy.repository.QuotationRepository;
import freezy.utils.Constants;
import freezy.utils.FreazyWhatsAppService;
import freezy.utils.StringUtils;
import freezy.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuotationService {

    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    QuotationItemsService quotationItemsService;

    @Autowired
    UtilsService utilsService;

    @Autowired
    ProductService productService;

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @Autowired
    FreazyWhatsAppService freazyWhatsAppService;

    @Autowired
    StringUtils stringUtils;

    @Autowired
    QuotationCreatedPublisher quotationCreatedPublisher;

    public List<Quotation> getAllQuotations() {
        return quotationRepository.findAll((Sort.by(Sort.Direction.DESC, "createdAt")));
    }

    public Quotation getQuotationById(String id) {
        return quotationRepository.findById(id).orElse(null);
    }

    public Quotation saveQuotation(QuotationDTO dto) {
        Quotation quotation = null;
        if(null != dto.getQuotationId()){
            quotation = getQuotationById(dto.getQuotationId());
        }
        if(null == quotation){
            quotation = new Quotation();
            quotation.setId(utilsService.generateId(Constants.QUOTATION_PREFIX));
            quotation.setStatus(QuotationStatus.DRAFT.toString());
        }

        Project project = null;
        if(null != dto.getProjectId()){
            project = projectService.getProjectById(dto.getProjectId());
        }
        if(null != project){
            quotation.setProject(project);
            quotation.setUser(project.getCustomer());
            quotation.setUserPersona(project.getCustomer().getRole().name());
        }
        else {
            quotation.setProject(null);
            quotation.setUser(userService.getUserById(dto.getUserId()));
            quotation.setUserPersona(dto.getUserPersona());
        }

        quotation.setCreatedAt(utilsService.generateDateFormat());
        quotation.setCreatedBy(utilsService.getSuperUser());
        quotation.setBudget(0);
        if(null != dto.getDiscount()){
            quotation.setDiscount(dto.getDiscount().doubleValue());
        }
        else{
            quotation.setDiscount(0.00);
        }


        Integer budget = 0;
        if(dto.getQuotationItems().size() > 0  && quotation.getQuotationItems() != null && quotation.getQuotationItems().size() > 0){
            quotationItemsService.deleteQuotationItems(quotation.getQuotationItems());
        }
        quotationRepository.saveAndFlush(quotation);
        List<QuotationItems> itemsEntity = new ArrayList<>();
        for (QuotationItemsDTO items: dto.getQuotationItems()) {
            QuotationItems item = new QuotationItems();
            item.setQuotation(quotation);
            item.setDiscountAmount(items.getDiscountAmount());
            if(null != items.getEffectivePrice() && items.getEffectivePrice() > 0){
                item.setUnitPrice(items.getUnitPrice());
                item.setEffectivePrice(items.getEffectivePrice());
                budget = budget + (items.getEffectivePrice() * items.getQuantity());
            }
            else{
                item.setEffectivePrice((int)(productService.getProductById(items.getProductId()).getCost()));
                item.setUnitPrice(items.getUnitPrice());
                budget = budget + (productService.getProductById(items.getProductId()).getCost() * items.getQuantity());
            }
            //item.setPrice((int)((productService.getProductById(items.getProductId())).getPrice() * (1 - (float)(dto.getDiscount())/100)));
            item.setQuantity(items.getQuantity());
            item.setId(utilsService.generateId(Constants.QUOTATION_ITEM_PREFIX));
            item.setCreatedAt(utilsService.generateDateFormat());
            item.setCreatedBy(utilsService.getSuperUser());
            item.setProduct(productService.getProductById(items.getProductId()));
            quotationItemsService.saveQuotationItems(item);

            itemsEntity.add(item);
        }
//        budget = (int)(budget * (1 - (float)(dto.getDiscount())/100));
        quotation.setQuotationItems(itemsEntity);
        quotation.setBudget(budget);
        quotationRepository.saveAndFlush(quotation);
        //String message = String.format(Constants.QUOTATION_CREATED_STRING, utilsService.getSuperUser().getFirst_name(),userService.getUserById(dto.getUserId()).getFirst_name());
        //freazyWhatsAppService.sendMessage(Constants.SEND_SMS, message);
        quotationCreatedPublisher.publishEvent(quotation.getId());
        return quotation;
    }

    public void deleteQuotation(String id) {
        quotationRepository.deleteById(id);
    }

    public void saveQuotation(Quotation quotation) {
        quotationRepository.saveAndFlush(quotation);
    }
}
