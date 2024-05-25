package freezy.controllers;

import freezy.dto.ProcurementDTO;
import freezy.entities.Procurement;
import freezy.services.ProcurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/procurements")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProcurementController {
    @Autowired
    private ProcurementService procurementService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProcurementDTO> getAllProcurements() {
        return procurementService.getAllProcurements();
    }

    @GetMapping("/{id}")
    public Procurement getProcurementById(@PathVariable String id) {
        return procurementService.getProcurementById(id);
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addProcurement(@RequestBody ProcurementDTO procurementDTO) {
        procurementService.saveProcurement(procurementDTO);
    }

    @PutMapping("/{id}")
    public void updateProcurement(@PathVariable String id, @RequestBody ProcurementDTO procurementDTO) {
        if (procurementService.getProcurementById(id) != null) {
            procurementService.saveProcurement(procurementDTO);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProcurement(@PathVariable String id) {
        procurementService.deleteProcurement(id);
    }
}
