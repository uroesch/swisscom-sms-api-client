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
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SmsTemplateTest {

    private final String API_KEY = "12345";
    private final String SENDER_NUMBER = "+41791234567";
    private final String RECEIVER_NUMBER = "+41791234568";
    private final String EXPECTED_REQUEST_URI_AS_STRING = "https://api.swisscom.com/v1/messaging/sms/outbound/tel%3A%2B41791234567/requests";

    SmsTemplate smsTemplate;

    @Mock
    RestTemplate restTemplate;

    @Before
    public void init() {
        smsTemplate = new SmsTemplate(API_KEY, SENDER_NUMBER);
        smsTemplate.restTemplate = restTemplate;
    }

    @Test
    public void sendSmsTest() {
        smsTemplate.sendSms("test", RECEIVER_NUMBER);
        verify(restTemplate, times(1)).postForObject(any(URI.class), anyObject(), any(Class.class));
    }

    @Test
    public void createHeadersTest() {
        HttpHeaders headers = smsTemplate.createHeaders();
        assertEquals(API_KEY, headers.get("client_id").get(0));
    }

    @Test
    public void createRequestUri() {
        assertEquals(URI.create(EXPECTED_REQUEST_URI_AS_STRING), smsTemplate.createRequestUri());
    }
}