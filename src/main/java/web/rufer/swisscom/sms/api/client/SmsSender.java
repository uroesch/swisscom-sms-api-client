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
package web.rufer.swisscom.sms.api.client;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import web.rufer.swisscom.sms.api.domain.OutboundSMSMessageRequest;
import web.rufer.swisscom.sms.api.domain.OutboundSMSTextMessage;
import web.rufer.swisscom.sms.api.domain.SendSMSRequest;
import web.rufer.swisscom.sms.api.factory.HeaderFactory;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;


public class SmsSender {

    private static final String API_URI_PREFIX = "https://api.swisscom.com/v1/messaging/sms/outbound/tel%3A%2B";
    private static final String API_URI_SUFFIX = "/requests";
    private static final String NUMBER_PREFIX = "tel:";
    private static final String DELIMITER = "";

    private String apiKey;
    private String senderNumber;
    protected RestTemplate restTemplate;

    /**
     * Constructor
     *
     * @param apiKey the API key from developer.swisscom.com
     * @param senderNumber the number of the sender (i.e. +41791234567)
     */
    public SmsSender(String apiKey, String senderNumber) {
        this.apiKey = apiKey;
        this.senderNumber = senderNumber;
        restTemplate = new RestTemplate();
    }

    /**
     * Send a sms to one or more receivers
     *
     * @param message the message text
     * @param receiverNumbers the numbers of the receivers (i.e. +41791234567)
     */
    public void sendSms(String message, String... receiverNumbers) {
        SendSMSRequest sendSMSRequest = new SendSMSRequest();
        sendSMSRequest.setOutboundSMSMessageRequest(createOutboundSMSMessageRequest(message, receiverNumbers));
        restTemplate.postForObject(createRequestUri(), new HttpEntity(sendSMSRequest, HeaderFactory.createHeaders(apiKey)), HttpEntity.class);
    }

    protected OutboundSMSMessageRequest createOutboundSMSMessageRequest(String message, String[] receiverNumbers) {
        OutboundSMSMessageRequest outboundSMSMessageRequest = new OutboundSMSMessageRequest();
        outboundSMSMessageRequest.setSenderAddress(String.join(DELIMITER, NUMBER_PREFIX, senderNumber));
        outboundSMSMessageRequest.setAddress(prefixAndAddReceiverNumbersToList(receiverNumbers));
        outboundSMSMessageRequest.setOutboundSMSTextMessage(new OutboundSMSTextMessage(message));
        return outboundSMSMessageRequest;
    }

    protected List<String> prefixAndAddReceiverNumbersToList(String[] receiverNumbers) {
        List<String> receivers = new LinkedList();
        for (String receiverNumber : receiverNumbers) {
            receivers.add(String.join(DELIMITER, NUMBER_PREFIX, receiverNumber));
        }
        return receivers;
    }

    protected URI createRequestUri() {
        return URI.create(String.join(DELIMITER, API_URI_PREFIX, senderNumber.substring(1), API_URI_SUFFIX));
    }
}