package freezy.services.v1;


import freezy.dto.v1.AccessoryDTOV1;
import freezy.dto.v1.ServiceDTOV1;
import freezy.entities.v1.AccessoryV1;
import freezy.entities.v1.ServiceV1;
import freezy.repository.v1.AccessoryRepositoryV1;
import freezy.repository.v1.ServiceRepositoryV1;
import freezy.repository.v1.ServiceTierRepositoryV1;
import freezy.utils.Constants;
import freezy.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicesServiceV1 {
    @Autowired
    private ServiceRepositoryV1 serviceRepositoryV1;

    @Autowired
    UtilsService utilsService;

    @Autowired
    ServiceTierRepositoryV1 serviceTierRepositoryV1;

    @Autowired
    CategoryServiceV1 categoryServiceV1;



    public List<ServiceV1> getAllServices() {
        return serviceRepositoryV1.findAll();
    }

    public ServiceV1 getServiceById(String id) {
        return serviceRepositoryV1.findById(id).orElse(null);
    }

    public ServiceV1 saveService(ServiceDTOV1 dto) {
        ServiceV1 service = new ServiceV1();
        if(null != dto){
            service.setId(utilsService.generateId(Constants.SERVICE_ORDER_PREFIX));
            service.setName(dto.getName());
            service.setCategory(categoryServiceV1.getCategoryById(dto.getCategoryId()));
            service.setDescription(dto.getDescription());
            if(null != dto.getCost()){
                service.setCost(dto.getCost());
            }
            else{
                service.setCost(0);
            }
            if(null != dto.getServiceTierId())service.setServiceTier(serviceTierRepositoryV1.findById(dto.getServiceTierId()).get());
            serviceRepositoryV1.saveAndFlush(service);
        }
        return service;
    }

    public void deleteService(String id) {
        serviceRepositoryV1.deleteById(id);
    }


    // Other methods as needed
}
