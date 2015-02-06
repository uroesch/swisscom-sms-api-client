package web.rufer.swisscom.sms.api.validation;

public abstract class AbstractValidator {

    protected AbstractValidator nextValidator;

    public abstract <T> void validate(T... objectsToValidate);

    public void setNextValidator(AbstractValidator nextValidator) {
        this.nextValidator = nextValidator;
    }
}
