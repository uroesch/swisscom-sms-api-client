package web.rufer.swisscom.sms.api.domain;

import java.util.List;

public class DeliveryInfoList {

    private List<DeliveryInfo> deliveryInfo;

    private String resourceURL;

    public DeliveryInfoList() {
    }

    public List<DeliveryInfo> getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(List<DeliveryInfo> deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public String getResourceURL() {
        return resourceURL;
    }

    public void setResourceURL(String resourceURL) {
        this.resourceURL = resourceURL;
    }
}
