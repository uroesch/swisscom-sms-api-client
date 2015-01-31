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

import web.rufer.swisscom.sms.api.exception.InvalidPhoneNumberException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidator {

    private static final String PHONE_NUMBER_REGEXP = "(\\+41)(\\d{9})";

    public static void validatePhoneNumber(String phoneNumberToCheck) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEXP);
        Matcher matcher = pattern.matcher(phoneNumberToCheck);
        if (invalidPhoneNumber(matcher)) {
            throw new InvalidPhoneNumberException("The phone number '" + phoneNumberToCheck + "' is invalid!");
        }
    }

    private static boolean invalidPhoneNumber(Matcher matcher) {
        return !matcher.matches();
    }

    public static void validatePhoneNumbers(String... phoneNumbersToCheck) {
        for (String phoneNumberToCheck : phoneNumbersToCheck) {
            validatePhoneNumber(phoneNumberToCheck);
        }
    }
}
