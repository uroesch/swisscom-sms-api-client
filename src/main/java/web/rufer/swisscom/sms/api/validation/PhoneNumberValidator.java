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

import web.rufer.swisscom.sms.api.exception.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidator extends AbstractValidator {

    private final String PHONE_NUMBER_REGEXP = "(\\+41)(\\d{9})";

    @Override
    public <T> void validate(T... objectsToValidate) {
        for (T t : objectsToValidate) {
            Matcher matcher = Pattern.compile(PHONE_NUMBER_REGEXP).matcher(t.toString());
            if (numberMatchesRegexp(matcher)) {
                throw new ValidationException("The phone number '" + t.toString() + "' is invalid!");
            }
        }
        if (null != nextValidator) nextValidator.validate(objectsToValidate);
    }

    protected boolean numberMatchesRegexp(Matcher matcher) {
        return !matcher.matches();
    }
}
