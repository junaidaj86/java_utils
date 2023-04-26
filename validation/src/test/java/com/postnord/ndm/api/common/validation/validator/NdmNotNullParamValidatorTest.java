package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmNotNullParam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
 class NdmNotNullParamValidatorTest {

    private NdmNotNullParamValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        cut = new NdmNotNullParamValidator();
    }

    @Test
     void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmNotNullParam
        final class SampleClass {
        } //inner class hack
        final NdmNotNullParam iotaNotNullParam = SampleClass.class.getAnnotation(NdmNotNullParam.class);
        assertDoesNotThrow(() -> cut.initialize(iotaNotNullParam), "exception should not occur during initialization");
    }

    @Test
     void whenObjectIsNotNullThenItIsConsideredValid() {

        assertTrue(cut.isValid("1234", mockConstraintValidatorContext),
                "object is not null, should be valid");
    }

    @Test
     void whenObjectIsNullThenItIsConsideredNotValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "object is null, should be not valid");
    }
}
