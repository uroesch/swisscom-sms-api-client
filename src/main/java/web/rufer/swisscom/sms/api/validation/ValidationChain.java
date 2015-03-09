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

import java.util.LinkedList;
import java.util.List;

public class ValidationChain {

    private List<ValidationStrategy> validationStrategies;

    private ValidationChain(Builder builder) {
        this.validationStrategies = builder.validationStrategies;
    }

    public List<ValidationStrategy> getValidationStrategies() {
        return validationStrategies;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void executeValidation(String... numbersToValidate) {
        for (ValidationStrategy validationStrategy : validationStrategies) {
            validationStrategy.validate(numbersToValidate);
        }
    }

    public static class Builder {

        private List<ValidationStrategy> validationStrategies = new LinkedList();

        public ValidationChain build() {
            return new ValidationChain(this);
        }

        public Builder add(ValidationStrategy validationStrategy) {
            this.validationStrategies.add(validationStrategy);
            return this;
        }
    }
}
