package freezy.services.v1;

import freezy.dto.v1.CashFlowV1;
import freezy.repository.v1.CashFlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CashFlowService {

    @Autowired
    CashFlowRepository cashFlowRepository;
    public List<CashFlowV1> getCashFlow(){
        List<CashFlowV1> cashFlows = new ArrayList<>();

        return cashFlows;
    }
}
