package freezy.dto;

import java.util.List;

public class FulfillmentDTO {

    String poId;
    List<FulfillmentItemsDTO> fulfillmentItemsDTOs;

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public List<FulfillmentItemsDTO> getFulfillmentItemsDTOs() {
        return fulfillmentItemsDTOs;
    }

    public void setFulfillmentItemsDTOs(List<FulfillmentItemsDTO> fulfillmentItemsDTOs) {
        this.fulfillmentItemsDTOs = fulfillmentItemsDTOs;
    }
}
