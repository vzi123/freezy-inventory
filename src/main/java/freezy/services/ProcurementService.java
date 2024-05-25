package freezy.services;

import freezy.dto.InventoryDTO;
import freezy.dto.ProcurementDTO;
import freezy.entities.Procurement;
import freezy.repository.ProcurementRepository;
import freezy.utils.Constants;
import freezy.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcurementService {
    @Autowired
    private ProcurementRepository procurementRepository;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    UtilsService utilsService;

    @Autowired
    ProductService productService;

    public List<ProcurementDTO> getAllProcurements() {
        List<Procurement> procurements = procurementRepository.findAll();
        List<ProcurementDTO> procurementDTOS = new ArrayList<ProcurementDTO>();
        for(Procurement procurement: procurements){
            ProcurementDTO dto = new ProcurementDTO();
            dto.setId(procurement.getId());
            dto.setCost(procurement.getCost());
            dto.setVendorId(procurement.getVendor());
            dto.setDescription(procurement.getDescription());
            dto.setProductId(procurement.getProduct().getId());
            dto.setProductName(procurement.getProduct().getName());
            dto.setQuantity(procurement.getQuantity());
            procurementDTOS.add(dto);
        }
        return procurementDTOS;
    }

    public Procurement getProcurementById(String id) {
        return procurementRepository.findById(id).orElse(null);
    }

    public void saveProcurement(ProcurementDTO procurementDTO) {

        Procurement procurement = null;
        if(null != procurementDTO.getId()){
            procurement = getProcurementById(procurementDTO.getId());
        }
        else{
            procurement = new Procurement();
        }

        procurement.setId(utilsService.generateId(Constants.PROC_PREFIX));
        procurement.setDate(utilsService.generateDateFormat());
        procurement.setProduct(productService.getProductById(procurementDTO.getProductId()));
        procurement.setQuantity(procurementDTO.getQuantity());
        procurement.setCost(procurementDTO.getCost());
        procurement.setVendor(procurementDTO.getVendorId());
        procurement.setDescription("Procured on " + utilsService.generateDateFormat() + " from " + procurementDTO.getVendorId());
        procurementRepository.save(procurement);
        InventoryDTO dto = new InventoryDTO();
        dto.setProductId(procurement.getProduct().getId());
        dto.setStock(procurement.getQuantity());
        inventoryService.saveInventory(dto);
    }

    public void deleteProcurement(String id) {
        procurementRepository.deleteById(id);
    }
}

