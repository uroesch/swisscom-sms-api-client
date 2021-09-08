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

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * Factory class for generating request headers.
 */
public class HeaderFactory {

    private static final String CLIENT_ID      = "client_id";
    private static final String SCS_REQUEST_ID = "SCS-Request-ID";
    private static final String SCS_VERSION    = "SCS-Version";

    /**
     * Creates http headers object based, which contains given api-key
     * @param apiKey the api-key to enrich the headers with
     * @param requestId unique string represnting the message identifier 
     * @return the created headers containing the api-key
     */
    public static HttpHeaders createHeaders(String apiKey, String requestId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.set(CLIENT_ID, apiKey);
        headers.set(SCS_REQUEST_ID, requestId);
        headers.set(SCS_VERSION, "2");
        return headers;
    }
}
