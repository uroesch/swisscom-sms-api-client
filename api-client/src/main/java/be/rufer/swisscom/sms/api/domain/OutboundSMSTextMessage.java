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
package be.rufer.swisscom.sms.api.domain;

/**
 * The sms text message object, which holds the sms message to send.
 *
 * Description for all the properties can be found under the following link:
 * @see <a href="https://developer.swisscom.com/documentation/api/products/SMS%20Messaging">Swisscom-SMS-API-Documentation</a>
 */
public class OutboundSMSTextMessage {

    private String message;

    public OutboundSMSTextMessage() {
    }

    public OutboundSMSTextMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
