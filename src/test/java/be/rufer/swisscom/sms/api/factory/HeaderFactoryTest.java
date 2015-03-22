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
package be.rufer.swisscom.sms.api.factory;

import be.rufer.swisscom.sms.api.factory.HeaderFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class HeaderFactoryTest {

    private static final String API_KEY = "12345";
    private static final String CLIENT_ID = "client_id";

    @Before
    public void init() {
        // Put test initialization here
    }

    @Test
    public void createHeadersReturnsHeadersWithAPIKey() {
        HttpHeaders headers = HeaderFactory.createHeaders(API_KEY);
        assertEquals(API_KEY, headers.get(CLIENT_ID).get(0));
    }

    @Test
    public void createHeadersReturnsHeadersWithContentTypeApplicationJson() {
        HttpHeaders headers = HeaderFactory.createHeaders(API_KEY);
        assertEquals(MediaType.APPLICATION_JSON, headers.getContentType());
    }

    @Test
    public void createHeadersReturnsHeadersWithAcceptApplicationJson() {
        HttpHeaders headers = HeaderFactory.createHeaders(API_KEY);
        assertArrayEquals(Arrays.asList(MediaType.APPLICATION_JSON).toArray(), headers.getAccept().toArray());
    }

    @Test
    public void createHeadersWithNullValueReturnsHeadersWithNullApiKey() {
        HttpHeaders headers = HeaderFactory.createHeaders(null);
        assertEquals(null, headers.get(CLIENT_ID).get(0));
    }
}
