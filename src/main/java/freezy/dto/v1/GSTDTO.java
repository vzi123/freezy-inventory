package freezy.dto.v1;

public class GSTDTO {
    String gstRate = "";
    Double gstValue = 0.0;

    public String getGstRate() {
        return gstRate;
    }

    public void setGstRate(String gstRate) {
        this.gstRate = gstRate;
    }

    public Double getGstValue() {
        return gstValue;
    }

    public void setGstValue(Double gstValue) {
        this.gstValue = gstValue;
    }
}
