package freezy.controllers.v1;

import freezy.dto.v1.CashFlowV1;
import freezy.entities.v1.CategoryV1;
import freezy.repository.v1.CashFlowRepository;
import freezy.services.v1.CashFlowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/cashflow")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CashFlowController {

    @Autowired
    CashFlowService cashFlowService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CashFlowV1> getCashFlow() {

        log.info("here");
        return cashFlowService.getCashFlow();
    }
}
