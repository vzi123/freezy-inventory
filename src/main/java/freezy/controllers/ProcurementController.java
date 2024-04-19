package freezy.controllers;

import freezy.entities.Procurement;
import freezy.services.ProcurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/procurements")
public class ProcurementController {
    @Autowired
    private ProcurementService procurementService;

    @GetMapping
    public List<Procurement> getAllProcurements() {
        return procurementService.getAllProcurements();
    }

    @GetMapping("/{id}")
    public Procurement getProcurementById(@PathVariable String id) {
        return procurementService.getProcurementById(id);
    }

    @PostMapping
    public void addProcurement(@RequestBody Procurement procurement) {
        procurementService.saveProcurement(procurement);
    }

    @PutMapping("/{id}")
    public void updateProcurement(@PathVariable String id, @RequestBody Procurement procurement) {
        if (procurementService.getProcurementById(id) != null) {
            procurementService.saveProcurement(procurement);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProcurement(@PathVariable String id) {
        procurementService.deleteProcurement(id);
    }
}
