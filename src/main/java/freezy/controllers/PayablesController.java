package freezy.controllers;


import freezy.entities.Category;
import freezy.entities.Payable;
import freezy.services.CategoryService;
import freezy.services.PayableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/payables")
public class PayablesController {
    @Autowired
    private PayableService payableService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Payable> getAllPayables() {

        log.info("here");
        return payableService.getAllPayables();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Payable getPayableById(@PathVariable String id) {
      return payableService.getPayableById(id);

    }

    @PostMapping
    public void addPayable(@RequestBody Payable payable) {
        payableService.savePayable(payable);
    }

    @PutMapping("/{id}")
    public void updatePayable(@PathVariable String id, @RequestBody Payable payable) {
        if (payableService.getPayableById(id) != null) {
            payableService.savePayable(payable);
        }
    }

    @DeleteMapping("/{id}")
    public void deletePayable(@PathVariable String id) {
        payableService.deletePayable(id);
    }
}
