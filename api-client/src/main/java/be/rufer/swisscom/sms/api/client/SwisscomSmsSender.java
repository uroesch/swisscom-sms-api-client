// vim: set shiftwidth=4 tabstop=4 softtabstop=4 expandtab :
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
package be.rufer.swisscom.sms.api.client;

import be.rufer.swisscom.sms.api.domain.CommunicationWrapper;
import be.rufer.swisscom.sms.api.domain.OutboundSMSMessageRequest;
import be.rufer.swisscom.sms.api.domain.OutboundSMSTextMessage;
import be.rufer.swisscom.sms.api.factory.HeaderFactory;
import be.rufer.swisscom.sms.api.validation.ValidationChain;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import be.rufer.swisscom.sms.api.validation.strategy.PhoneNumberRegexpValidationStrategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

/**
 * For every api-key an instance of this class can be created. The swisscom sms sender provides
 * methods to send sms messages to one or several receivers.
 */
public class SwisscomSmsSender {

    private static final String API_URI = "https://api.swisscom.com/messaging/sms";

    private String apiKey;
    private String senderNumber;
    private String senderName;
    private String clientCorrelator;
    private String requestId; 
    private ValidationChain validationChain = ValidationChain.builder().add(new PhoneNumberRegexpValidationStrategy()).build();
    protected RestTemplate restTemplate;

    /**
     * Constructor
     *
     * @param apiKey the API-key generated by http://developer.swisscom.com
     * @param senderNumber the number of the sender (i.e. +41791234567)
     */
    public SwisscomSmsSender(String apiKey, String senderNumber) {
        validationChain.executeValidation(senderNumber);
        this.apiKey = apiKey;
        this.senderNumber = senderNumber;
        this.restTemplate = new RestTemplate();
        this.requestId= UUID.randomUUID().toString();
    }

    /**
     * Extended Constructor
     *
     * @param apiKey the API-key generated by http://developer.swisscom.com
     * @param senderNumber the number of the sender (i.e. +41791234567)
     * @param senderName [ONLY IN PARTNER MODE] Name of the sender, which should be displayed on the receivers phone
     * @param clientCorrelator An id that can be found in the logs of Swisscom
     */
    public SwisscomSmsSender(String apiKey, String senderNumber,  String senderName, String clientCorrelator) {
        validationChain.executeValidation(senderNumber); 
        this.apiKey = apiKey;
        this.senderNumber = senderNumber;
        this.senderName = senderName.toString();
        this.clientCorrelator = clientCorrelator.toString();
        this.restTemplate = new RestTemplate();
        this.requestId= UUID.randomUUID().toString();
    }

    /**
     * Send a sms to one or more receivers
     *
     * @param message the message text
     * @param receiverNumbers the numbers of the receivers (i.e. +41791234567)
     * @return communication wrapper object containing delivery information
     */
    public CommunicationWrapper sendSms(String message, String receiverNumber) {
        // not required
        // validationChain.executeValidation(receiverNumber);
        CommunicationWrapper communicationWrapper = new CommunicationWrapper();
        communicationWrapper.setOutboundSMSMessageRequest(createOutboundSMSMessageRequest(message, receiverNumber));
        return restTemplate.postForObject(
            URI.create(API_URI),
            new HttpEntity(
                communicationWrapper.getOutboundSMSMessageRequest().toJson(),
                HeaderFactory.createHeaders(apiKey, requestId)
            ),
            CommunicationWrapper.class

        );
    }

    protected OutboundSMSMessageRequest createOutboundSMSMessageRequest(String message, String receiverNumber) {
        OutboundSMSMessageRequest smsMessageRequest = new OutboundSMSMessageRequest();
        smsMessageRequest.setSenderAddress(senderNumber);
        smsMessageRequest.setAddress(receiverNumber);
        smsMessageRequest.setOutboundSMSTextMessage(new OutboundSMSTextMessage(message));
        smsMessageRequest.setSenderName(senderName);
        smsMessageRequest.setClientCorrelator(clientCorrelator);
        return smsMessageRequest;
    }
}
