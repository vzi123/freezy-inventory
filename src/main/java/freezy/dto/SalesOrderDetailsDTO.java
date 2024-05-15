package freezy.dto;

import java.util.List;

public class SalesOrderDetailsDTO {

    String userId;
    String userPersona;
    String poId;
    List<SOItemsDTO> soItems;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPersona() {
        return userPersona;
    }

    public void setUserPersona(String userPersona) {
        this.userPersona = userPersona;
    }

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
