/*
 * Copyright (C) 2015 Marc Rufer (m.rufer@gmx.ch)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package web.rufer.swisscom.sms.api.domain;

import java.util.List;

/**
 * Domain object, which holds information about the delivery.
 */
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
