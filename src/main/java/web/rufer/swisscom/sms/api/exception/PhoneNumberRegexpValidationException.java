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
package web.rufer.swisscom.sms.api.exception;

public class PhoneNumberRegexpValidationException extends ValidationException {

    private static final String EXCEPTION_MESSAGE_TEMPLATE = "Validation failed. The phone number '%s' is not matching the validation pattern: '%s'";

    public PhoneNumberRegexpValidationException(String invalidPhoneNumber, String regexp) {
        super(String.format(EXCEPTION_MESSAGE_TEMPLATE, invalidPhoneNumber, regexp));
    }
}
