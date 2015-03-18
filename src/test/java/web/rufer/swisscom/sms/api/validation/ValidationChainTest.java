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
package web.rufer.swisscom.sms.api.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import web.rufer.swisscom.sms.api.validation.strategy.PhoneNumberRegexpValidationStrategy;
import web.rufer.swisscom.sms.api.validation.strategy.ValidationStrategy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ValidationChainTest {

    private final String VALID_PHONE_NUMBER = "+41791234567";

    private ValidationChain validationChain;

    @Mock
    private ValidationStrategy sampleValidationStrategy1;

    @Mock
    private ValidationStrategy sampleValidationStrategy2;

    @Test
    public void validationChainBuilderCreatesNewValidationChainWithValidators() {
        validationChain = ValidationChain.builder().add(new PhoneNumberRegexpValidationStrategy()).build();
        assertEquals(1, validationChain.getValidationStrategies().size());
    }

    @Test
    public void executeValidationCallsAllAddedValidators() {
        validationChain = ValidationChain.builder().add(sampleValidationStrategy1).add(sampleValidationStrategy2).build();
        validationChain.executeValidation(VALID_PHONE_NUMBER);
        verify(sampleValidationStrategy1).validate(eq(VALID_PHONE_NUMBER));
        verify(sampleValidationStrategy2).validate(eq(VALID_PHONE_NUMBER));
    }
}
