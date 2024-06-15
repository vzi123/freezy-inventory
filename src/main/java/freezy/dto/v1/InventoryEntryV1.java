package freezy.dto.v1;

import java.util.List;

public class InventoryEntryV1 {
    String comments;
    String userId;
    List<InventoryDTOV1> inventories;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<InventoryDTOV1> getInventories() {
        return inventories;
    }

    public void setInventories(List<InventoryDTOV1> inventories) {
        this.inventories = inventories;
    }
}
