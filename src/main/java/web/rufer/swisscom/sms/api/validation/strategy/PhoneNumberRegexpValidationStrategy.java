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
package web.rufer.swisscom.sms.api.validation.strategy;

import web.rufer.swisscom.sms.api.validation.exception.PhoneNumberRegexpValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberRegexpValidationStrategy implements ValidationStrategy {

    private static final String PHONE_NUMBER_REGEXP = "(\\+41)(\\d{9})";
    private final Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEXP);

    @Override
    public <T> void validate(T... objectsToValidate) {
        for (T t : objectsToValidate) {
            Matcher matcher = pattern.matcher(t.toString());
            if (numberMatchesRegexp(matcher)) {
                throw new PhoneNumberRegexpValidationException(t.toString(), PHONE_NUMBER_REGEXP);
            }
        }
    }

    protected boolean numberMatchesRegexp(Matcher matcher) {
        return !matcher.matches();
    }
}
