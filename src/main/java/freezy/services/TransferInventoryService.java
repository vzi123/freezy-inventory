package freezy.services;


import freezy.entities.TransferInventory;
import freezy.repository.TransferInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferInventoryService {
    @Autowired
    private TransferInventoryRepository transferInventoryRepository;

    public List<TransferInventory> getAllTransferInventories() {
        return transferInventoryRepository.findAll();
    }

    public TransferInventory getTransferInventoryById(String id) {
        return transferInventoryRepository.findById(id).orElse(null);
    }

    public void saveTransferInventory(TransferInventory transferInventory) {
        transferInventoryRepository.save(transferInventory);
    }

    public void deleteTransferInventory(String id) {
        transferInventoryRepository.deleteById(id);
    }
}
