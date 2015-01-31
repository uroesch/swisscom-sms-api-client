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
package web.rufer.swisscom.sms.api.validator;

import org.junit.Before;
import org.junit.Test;
import web.rufer.swisscom.sms.api.exception.InvalidPhoneNumberException;
import web.rufer.swisscom.sms.api.validation.PhoneNumberValidator;

public class PhoneNumberValidatorTest {

    private final String VALID_PHONE_NUMBER = "+41791234567";
    private final String INVALID_PHONE_NUMBER = "41791234567";
    private final String[] PHONE_NUMBERS_TO_CHECK = {VALID_PHONE_NUMBER, INVALID_PHONE_NUMBER};

    @Before
    public void init() {
        // Put test initialization here
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void validatePhoneNumberThrowsInvalidNumberExceptionForInvalidPhoneNumber() {
        PhoneNumberValidator.validatePhoneNumber(INVALID_PHONE_NUMBER);
    }

    @Test(expected = InvalidPhoneNumberException.class)
    public void validatePhoneNumbersThrowsInvalidNumberExceptionForArrayWithInvalidPhoneNumber() {
        PhoneNumberValidator.validatePhoneNumbers(PHONE_NUMBERS_TO_CHECK);
    }
}
