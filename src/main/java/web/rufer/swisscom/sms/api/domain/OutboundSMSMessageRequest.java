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
 * The SMS Request object. Description for all the properties can be found under the following link: {@linktourl https://developer.swisscom.com/documentation/api/products/SMS%20Messaging}
 */
public class OutboundSMSMessageRequest {

    private List<String> address;

    private String senderAddress;

    private OutboundSMSTextMessage outboundSMSTextMessage;

    private String clientCorrelator;

    private String senderName;

    private DeliveryInfoList deliveryInfoList;

    public OutboundSMSMessageRequest() {
    }

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public OutboundSMSTextMessage getOutboundSMSTextMessage() {
        return outboundSMSTextMessage;
    }

    public void setOutboundSMSTextMessage(OutboundSMSTextMessage outboundSMSTextMessage) {
        this.outboundSMSTextMessage = outboundSMSTextMessage;
    }

    public String getClientCorrelator() {
        return clientCorrelator;
    }

    public void setClientCorrelator(String clientCorrelator) {
        this.clientCorrelator = clientCorrelator;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public DeliveryInfoList getDeliveryInfoList() {
        return deliveryInfoList;
    }

    public void setDeliveryInfoList(DeliveryInfoList deliveryInfoList) {
        this.deliveryInfoList = deliveryInfoList;
    }
}
