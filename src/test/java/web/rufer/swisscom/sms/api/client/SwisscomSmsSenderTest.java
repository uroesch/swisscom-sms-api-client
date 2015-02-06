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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import web.rufer.swisscom.sms.api.domain.CommunicationWrapper;
import web.rufer.swisscom.sms.api.domain.DeliveryInfo;
import web.rufer.swisscom.sms.api.domain.DeliveryInfoList;
import web.rufer.swisscom.sms.api.domain.OutboundSMSMessageRequest;
import web.rufer.swisscom.sms.api.exception.ValidationException;

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
    private final String CLIENT_CORRELATOR = "client-correlator";
    private final String DELIVERY_STATUS = "DeliveredToNetwork";
    private final String EXPECTED_SENDER_NUMBER = "tel:+41791234567";
    private final String EXPECTED_RECEIVER_NUMBER = "tel:+41791234568";
    private final String EXPECTED_REQUEST_URI_AS_STRING = "https://api.swisscom.com/v1/messaging/sms/outbound/tel%3A%2B41791234567/requests";

    private SwisscomSmsSender swisscomSmsSender;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void init() {
        swisscomSmsSender = new SwisscomSmsSender(API_KEY, SENDER_NUMBER);
        swisscomSmsSender.restTemplate = restTemplate;
    }

    @Test
    public void sendSmsCallsRestTemplatePostForObjectMethodOnce() {
        swisscomSmsSender.sendSms(SAMPLE_MESSAGE, RECEIVER_NUMBER);
        verify(restTemplate, times(1)).postForObject(any(URI.class), anyObject(), any(Class.class));
    }

    @Test
    public void sendSmsReturnsCommunicationWrapper() {
        when(restTemplate.postForObject(any(URI.class), anyObject(), any(Class.class))).thenReturn(createSampleCommunicationWrapper());
        CommunicationWrapper communicationWrapper = swisscomSmsSender.sendSms(SAMPLE_MESSAGE, RECEIVER_NUMBER);
        assertEquals(DELIVERY_STATUS, communicationWrapper.getOutboundSMSMessageRequest().getDeliveryInfoList().getDeliveryInfo().get(0).getDeliveryStatus());
    }

    private CommunicationWrapper createSampleCommunicationWrapper() {
        CommunicationWrapper communicationWrapper = new CommunicationWrapper();
        DeliveryInfoList deliveryInfoList = new DeliveryInfoList();
        DeliveryInfo deliveryInfo = new DeliveryInfo();
        deliveryInfo.setAddress(RECEIVER_NUMBER);
        deliveryInfo.setDeliveryStatus(DELIVERY_STATUS);
        deliveryInfoList.setDeliveryInfo(Arrays.asList(deliveryInfo));
        OutboundSMSMessageRequest outboundSMSMessageRequest = new OutboundSMSMessageRequest();
        outboundSMSMessageRequest.setDeliveryInfoList(deliveryInfoList);
        communicationWrapper.setOutboundSMSMessageRequest(outboundSMSMessageRequest);
        return communicationWrapper;
    }

    @Test
    public void createRequestUriReturnsURIWithSenderNumber() {
        assertEquals(URI.create(EXPECTED_REQUEST_URI_AS_STRING), swisscomSmsSender.createRequestUri());
    }

    @Test
    public void createOutboundSMSMessageRequestReturnsRequestObjectWithFilledOutRequiredFields() {
        OutboundSMSMessageRequest outboundSMSMessageRequest = swisscomSmsSender.createOutboundSMSMessageRequest(SAMPLE_MESSAGE, new String[]{RECEIVER_NUMBER});
        assertEquals(EXPECTED_RECEIVER_NUMBER, outboundSMSMessageRequest.getAddress().get(0));
        assertEquals(EXPECTED_SENDER_NUMBER, outboundSMSMessageRequest.getSenderAddress());
        assertEquals(SAMPLE_MESSAGE, outboundSMSMessageRequest.getOutboundSMSTextMessage().getMessage());
        assertEquals(null, outboundSMSMessageRequest.getSenderName());
        assertEquals(null, outboundSMSMessageRequest.getClientCorrelator());
    }

    @Test
    public void createOutboundSMSMessageRequestReturnsFullyFilledRequestObject() {
        swisscomSmsSender = new SwisscomSmsSender(API_KEY, SENDER_NUMBER, SENDER_NAME, CLIENT_CORRELATOR);
        swisscomSmsSender.restTemplate = restTemplate;
        OutboundSMSMessageRequest outboundSMSMessageRequest = swisscomSmsSender.createOutboundSMSMessageRequest(SAMPLE_MESSAGE, new String[]{RECEIVER_NUMBER});
        assertEquals(SENDER_NAME, outboundSMSMessageRequest.getSenderName());
        assertEquals(CLIENT_CORRELATOR, outboundSMSMessageRequest.getClientCorrelator());
    }

    @Test
    public void prefixAndAddReceiverNumbersToListReturnsListContainingNumbers() {
        String[] receiverArray = {RECEIVER_NUMBER};
        List receivers = swisscomSmsSender.prefixAndAddReceiverNumbersToList(receiverArray);
        String[] expectedResult = {EXPECTED_RECEIVER_NUMBER};
        assertArrayEquals(expectedResult, receivers.toArray());
    }

    @Test(expected = ValidationException.class)
         public void swisscomSmsSenderCreationThrowsValidationException() {
        new SwisscomSmsSender(API_KEY, INVALID_SENDER_NUMBER);
    }

    @Test(expected = ValidationException.class)
    public void swisscomSmsSenderCreationWithExtendedConstructorThrowsValidationException() {
        new SwisscomSmsSender(API_KEY, INVALID_SENDER_NUMBER, SENDER_NAME, CLIENT_CORRELATOR);
    }

    @Test(expected = ValidationException.class)
    public void sendSmsWithInvalidReceiverNumberThrowsValidationException() {
        swisscomSmsSender.sendSms(SAMPLE_MESSAGE, INVALID_RECEIVER_NUMBER);
    }

    @Test(expected = ValidationException.class)
    public void sendSmsWithInvalidReceiverNumberCollectionThrowsValidationException() {
        String[] phoneNumbers = new String[] {INVALID_RECEIVER_NUMBER, RECEIVER_NUMBER};
        swisscomSmsSender.sendSms(SAMPLE_MESSAGE, phoneNumbers);
    }
}