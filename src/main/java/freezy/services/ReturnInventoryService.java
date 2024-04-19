package freezy.services;


import freezy.entities.ReturnInventory;
import freezy.repository.ReturnInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReturnInventoryService {
    @Autowired
    private ReturnInventoryRepository returnInventoryRepository;

    public List<ReturnInventory> getAllReturnInventories() {
        return returnInventoryRepository.findAll();
    }

    public ReturnInventory getReturnInventoryById(String id) {
        return returnInventoryRepository.findById(id).orElse(null);
    }

    public void saveReturnInventory(ReturnInventory returnInventory) {
        returnInventoryRepository.save(returnInventory);
    }

    public void deleteReturnInventory(String id) {
        returnInventoryRepository.deleteById(id);
    }
}

