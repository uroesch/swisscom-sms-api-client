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

import be.rufer.swisscom.sms.api.client.SwisscomSmsSender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import be.rufer.swisscom.sms.api.domain.CommunicationWrapper;
import be.rufer.swisscom.sms.api.domain.DeliveryInfo;
import be.rufer.swisscom.sms.api.domain.DeliveryInfoList;
import be.rufer.swisscom.sms.api.domain.OutboundSMSMessageRequest;
import be.rufer.swisscom.sms.api.validation.exception.PhoneNumberRegexpValidationException;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SwisscomSmsSenderTest {

    private final String API_KEY = "12345";
    private final String SENDER_NAME = "Muster";
    private final String SENDER_NUMBER = "+41791234567";
    private final String INVALID_SENDER_NUMBER = "41791234567";
    private final String SAMPLE_MESSAGE = "test";
    private final String RECEIVER_NUMBER = "+41791234568";
    private final String INVALID_RECEIVER_NUMBER = "41791234568";
    private final String CALLBACK_URL = "https://foo.bar/callback";
    private final String EXPECTED_SENDER_NAME= "Muster";
    private final String EXPECTED_SENDER_NUMBER = "+41791234567";
    private final String EXPECTED_RECEIVER_NUMBER = "+41791234568";
    private final String SUCCESSFUL_DELIVERY_STATUS = "DeliveredToNetwork";
    private final String MINIMAL_JSON_PAYLOAD = String.format("{\"to\":\"%s\",\"text\":\"%s\"}", RECEIVER_NUMBER, SAMPLE_MESSAGE);
    private final String PARTNER_JSON_PAYLOAD = String.format("{\"from\":\"%s\",\"to\":\"%s\",\"text\":\"%s\"}", SENDER_NAME, RECEIVER_NUMBER, SAMPLE_MESSAGE);
    private final String CALLBACK_JSON_PAYLOAD = String.format("{\"from\":\"%s\",\"callbackUrl\":\"%s\",\"to\":\"%s\",\"text\":\"%s\"}", SENDER_NAME, CALLBACK_URL, RECEIVER_NUMBER, SAMPLE_MESSAGE);

    private SwisscomSmsSender swisscomSmsSender;
    private SwisscomSmsSender swisscomSmsSenderPartner;
    private SwisscomSmsSender swisscomSmsSenderCallback;
    private CommunicationWrapper communicationWrapper;


    @Mock
    private RestTemplate restTemplate;

    @Before
    public void init() {
        communicationWrapper = new CommunicationWrapper();
        swisscomSmsSender = new SwisscomSmsSender(API_KEY, SENDER_NUMBER);
        swisscomSmsSenderPartner = new SwisscomSmsSender(API_KEY, SENDER_NUMBER, SENDER_NAME);
        swisscomSmsSenderCallback = new SwisscomSmsSender(API_KEY, SENDER_NUMBER, SENDER_NAME, CALLBACK_URL);
        swisscomSmsSender.restTemplate = restTemplate;
    }

    @Test
     public void sendSmsCallsRestTemplatePostForObjectMethodOnce() {
        swisscomSmsSender.sendSms(SAMPLE_MESSAGE, RECEIVER_NUMBER);
        verify(restTemplate).postForObject(any(URI.class), anyObject(), any(Class.class));
    }

    @Test
    public void sendSmsReturnsCommunicationWrapper() {
        when(restTemplate.postForObject(any(URI.class), anyObject(), any(Class.class))).thenReturn(createSampleCommunicationWrapper());
        CommunicationWrapper communicationWrapper = swisscomSmsSender.sendSms(SAMPLE_MESSAGE, RECEIVER_NUMBER);
        assertEquals(SUCCESSFUL_DELIVERY_STATUS, communicationWrapper.getOutboundSMSMessageRequest().getDeliveryInfoList().getDeliveryInfo().get(0).getDeliveryStatus());
    }

    private CommunicationWrapper createSampleCommunicationWrapper() {
        CommunicationWrapper communicationWrapper = new CommunicationWrapper();
        DeliveryInfoList deliveryInfoList = new DeliveryInfoList();
        DeliveryInfo deliveryInfo = new DeliveryInfo();
        deliveryInfo.setAddress(RECEIVER_NUMBER);
        deliveryInfo.setDeliveryStatus(SUCCESSFUL_DELIVERY_STATUS);
        deliveryInfoList.setDeliveryInfo(Arrays.asList(deliveryInfo));
        OutboundSMSMessageRequest outboundSMSMessageRequest = new OutboundSMSMessageRequest();
        outboundSMSMessageRequest.setDeliveryInfoList(deliveryInfoList);
        communicationWrapper.setOutboundSMSMessageRequest(outboundSMSMessageRequest);
        return communicationWrapper;
    }

    @Test
    public void createOutboundSMSMessageRequestReturnsRequestObjectWithFilledOutRequiredFields() {
        OutboundSMSMessageRequest outboundSMSMessageRequest = swisscomSmsSender.createOutboundSMSMessageRequest(SAMPLE_MESSAGE, RECEIVER_NUMBER);
        assertEquals(EXPECTED_RECEIVER_NUMBER, outboundSMSMessageRequest.getAddress());
        assertEquals(EXPECTED_SENDER_NUMBER, outboundSMSMessageRequest.getSenderAddress());
        assertEquals(SAMPLE_MESSAGE, outboundSMSMessageRequest.getOutboundSMSTextMessage().getMessage());
        assertEquals(MINIMAL_JSON_PAYLOAD, outboundSMSMessageRequest.toJson());
        assertEquals(null, outboundSMSMessageRequest.getSenderName());
        assertEquals(null, outboundSMSMessageRequest.getCallbackUrl());
    }

    @Test
    public void createOutboundSMSMessageRequestInPartnerModeReturnsRequestObjectWithFilledOutRequiredFields() {
        OutboundSMSMessageRequest outboundSMSMessageRequest = swisscomSmsSenderPartner.createOutboundSMSMessageRequest(SAMPLE_MESSAGE, RECEIVER_NUMBER);
        assertEquals(EXPECTED_RECEIVER_NUMBER, outboundSMSMessageRequest.getAddress());
        assertEquals(EXPECTED_SENDER_NUMBER, outboundSMSMessageRequest.getSenderAddress());
        assertEquals(EXPECTED_SENDER_NAME, outboundSMSMessageRequest.getSenderName());
        assertEquals(SAMPLE_MESSAGE, outboundSMSMessageRequest.getOutboundSMSTextMessage().getMessage());
        assertEquals(PARTNER_JSON_PAYLOAD, outboundSMSMessageRequest.toJson());
        assertEquals(SENDER_NAME, outboundSMSMessageRequest.getSenderName());
        assertEquals(null, outboundSMSMessageRequest.getCallbackUrl());
    }

    @Test
    public void createOutboundSMSMessageRequestInCallbackModeReturnsRequestObjectWithFilledOutRequiredFields() {
        OutboundSMSMessageRequest outboundSMSMessageRequest = swisscomSmsSenderCallback.createOutboundSMSMessageRequest(SAMPLE_MESSAGE, RECEIVER_NUMBER);
        assertEquals(EXPECTED_RECEIVER_NUMBER, outboundSMSMessageRequest.getAddress());
        assertEquals(EXPECTED_SENDER_NUMBER, outboundSMSMessageRequest.getSenderAddress());
        assertEquals(EXPECTED_SENDER_NAME, outboundSMSMessageRequest.getSenderName());
        assertEquals(SAMPLE_MESSAGE, outboundSMSMessageRequest.getOutboundSMSTextMessage().getMessage());
        assertEquals(CALLBACK_JSON_PAYLOAD, outboundSMSMessageRequest.toJson());
        assertEquals(SENDER_NAME, outboundSMSMessageRequest.getSenderName());
        assertEquals(CALLBACK_URL, outboundSMSMessageRequest.getCallbackUrl());
    }

    @Test
    public void createOutboundSMSMessageRequestReturnsFullyFilledRequestObject() {
        swisscomSmsSender = new SwisscomSmsSender(API_KEY, SENDER_NUMBER, SENDER_NAME, CALLBACK_URL);
        swisscomSmsSender.restTemplate = restTemplate;
        OutboundSMSMessageRequest outboundSMSMessageRequest = swisscomSmsSender.createOutboundSMSMessageRequest(SAMPLE_MESSAGE, RECEIVER_NUMBER);
        assertEquals(SENDER_NAME, outboundSMSMessageRequest.getSenderName());
        assertEquals(CALLBACK_URL, outboundSMSMessageRequest.getCallbackUrl());
    }

    @Test(expected = PhoneNumberRegexpValidationException.class)
     public void swisscomSmsSenderCreationThrowsValidationException() {
        new SwisscomSmsSender(API_KEY, INVALID_SENDER_NUMBER);
    }

    @Test(expected = PhoneNumberRegexpValidationException.class)
    public void swisscomSmsSenderCreationWithExtendedConstructorThrowsValidationException() {
        new SwisscomSmsSender(API_KEY, INVALID_SENDER_NUMBER, SENDER_NAME, CALLBACK_URL);
    }

    /*
    @Test(expected = PhoneNumberRegexpValidationException.class)
    public void sendSmsWithInvalidReceiverNumberThrowsValidationException() {
        swisscomSmsSender.sendSms(SAMPLE_MESSAGE, INVALID_RECEIVER_NUMBER);
    }
    */
}

// vim: set shiftwidth=4 softtabstop=4 expandtab :
