package freezy.controllers;


import freezy.entities.Category;
import freezy.entities.Receivable;
import freezy.services.CategoryService;
import freezy.services.ReceivableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/receivables")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReceivablesController {
    @Autowired
    private ReceivableService receivableService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Receivable> getAllReceivables() {

        log.info("here");
        return receivableService.getAllReceivables();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Receivable getReceivableById(@PathVariable String id) {
      return receivableService.getReceivableById(id);

    }

    @PostMapping
    public void addReceivable(@RequestBody Receivable receivable) {
        receivableService.saveReceivable(receivable);
    }

    @PutMapping("/{id}")
    public void updateReceivable(@PathVariable String id, @RequestBody Receivable receivable) {
        if (receivableService.getReceivableById(id) != null) {
            receivableService.saveReceivable(receivable);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteReceivable(@PathVariable String id) {
        receivableService.deleteReceivable(id);
    }
}
