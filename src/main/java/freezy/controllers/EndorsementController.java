package freezy.controllers;


import freezy.entities.Category;
import freezy.entities.Endorsement;
import freezy.services.CategoryService;
import freezy.services.EndorsementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/endorsements")
public class EndorsementController {
    @Autowired
    private EndorsementService endorsementService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Endorsement> getAllEndorsements() {

        log.info("here");
        return endorsementService.getAllEndorsements();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Endorsement getEndorsementById(@PathVariable String id) {
      return endorsementService.getEndorsementById(id);

    }

    @PostMapping
    public void saveEndorsement(@RequestBody Endorsement endorsement) {
        endorsementService.saveEndorsement(endorsement);
    }

    @PutMapping("/{id}")
    public void updateCategory(@PathVariable String id, @RequestBody Endorsement endorsement) {
        if (endorsementService.getEndorsementById(id) != null) {
            endorsementService.saveEndorsement(endorsement);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteEndorsement(@PathVariable String id) {
        endorsementService.deleteEndorsement(id);
    }
}
