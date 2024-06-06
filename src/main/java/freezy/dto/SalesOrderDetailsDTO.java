package freezy.dto;

import java.util.List;

public class SalesOrderDetailsDTO {

    String poId;
    List<SOItemsDTO> soItems;

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public List<SOItemsDTO> getSoItems() {
        return soItems;
    }

    public void setSoItems(List<SOItemsDTO> soItems) {
        this.soItems = soItems;
    }
}
