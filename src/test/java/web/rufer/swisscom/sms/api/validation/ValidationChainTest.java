package web.rufer.swisscom.sms.api.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ValidationChainTest {

    private final String VALID_PHONE_NUMBER = "+41791234567";

    private ValidationChain validationChain;

    @Mock
    private Validator sampleValidator1;

    @Mock
    private Validator sampleValidator2;

    @Test
    public void validationChainBuilderCreatesNewValidationChainWithValidators() {
        validationChain = ValidationChain.builder().add(new PhoneNumberRegexpValidator()).build();
        assertEquals(1, validationChain.getValidators().size());
    }

    @Test
    public void executeValidationCallsAllAddedValidators() {
        validationChain = ValidationChain.builder().add(sampleValidator1).add(sampleValidator2).build();
        validationChain.executeValidation(VALID_PHONE_NUMBER);
        verify(sampleValidator1, times(1)).validate(eq(VALID_PHONE_NUMBER));
        verify(sampleValidator2, times(1)).validate(eq(VALID_PHONE_NUMBER));
    }
}
