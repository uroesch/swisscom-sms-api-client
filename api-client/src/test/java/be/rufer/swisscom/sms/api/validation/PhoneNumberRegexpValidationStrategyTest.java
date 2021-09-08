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
package be.rufer.swisscom.sms.api.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import be.rufer.swisscom.sms.api.validation.exception.PhoneNumberRegexpValidationException;
import be.rufer.swisscom.sms.api.validation.strategy.PhoneNumberRegexpValidationStrategy;

@RunWith(MockitoJUnitRunner.class)
public class PhoneNumberRegexpValidationStrategyTest {

    private final String VALID_PHONE_NUMBER = "+41791234567";
    private final String INVALID_PHONE_NUMBER = "41791234567";
    private final String[] INVALID_PHONE_NUMBER_COLLECTION = {VALID_PHONE_NUMBER, INVALID_PHONE_NUMBER};
    private final String[] VALID_PHONE_NUMBER_COLLECTION = {VALID_PHONE_NUMBER, VALID_PHONE_NUMBER};

    private PhoneNumberRegexpValidationStrategy phoneNumberRegexpValidator;

    @Before
    public void init() {
        phoneNumberRegexpValidator = new PhoneNumberRegexpValidationStrategy();
    }

    @Test
    public void validateWithValidPhoneNumberCollectionRunsWithoutException() {
        phoneNumberRegexpValidator.validate(VALID_PHONE_NUMBER_COLLECTION);
    }

    @Test(expected = PhoneNumberRegexpValidationException.class)
         public void validateInvalidPhoneNumberThrowsInvalidNumberException() {
        phoneNumberRegexpValidator.validate(INVALID_PHONE_NUMBER);
    }

    @Test(expected = PhoneNumberRegexpValidationException.class)
    public void validateInvalidPhoneNumberCollectionThrowsInvalidNumberException() {
        phoneNumberRegexpValidator.validate(INVALID_PHONE_NUMBER_COLLECTION);
    }
}
