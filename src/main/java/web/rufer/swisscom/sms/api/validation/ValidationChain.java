package web.rufer.swisscom.sms.api.validation;

import java.util.LinkedList;
import java.util.List;

public class ValidationChain {

    private List<Validator> validators;

    private ValidationChain(Builder builder) {
        this.validators = builder.validators;
    }

    public List<Validator> getValidators() {
        return validators;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void executeValidation(String... T) {
        for (Validator validator : validators) {
            validator.validate(T);
        }
    }

    public static class Builder {

        private List<Validator> validators = new LinkedList<Validator>();

        public ValidationChain build() {
            return new ValidationChain(this);
        }

        public Builder add(Validator validator) {
            this.validators.add(validator);
            return this;
        }
    }
}
