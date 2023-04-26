package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmCollectionAllowedStrings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NdmCollectionAllowedStringsValidatorTest {

    private static final String ALLOWED = "ALLOWED";

    private static final String ALSO_ALLOWED = "ALSO_ALLOWED";

    private static final String ALLOWED_1 = "ALLOWED1";

    private static final String ALLOWED_2 = "ALLOWED2";

    private NdmCollectionAllowedStringsValidator cut;

    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
    void setUp() {
        @NdmCollectionAllowedStrings(values = {ALLOWED, ALSO_ALLOWED})
        final class SampleClass {
        }
        final NdmCollectionAllowedStrings allowedStrings = SampleClass.class.getAnnotation(NdmCollectionAllowedStrings.class);
        cut = new NdmCollectionAllowedStringsValidator();
        cut.initialize(allowedStrings);
    }

    @Test
    void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmCollectionAllowedStrings(values = {ALLOWED_1, ALLOWED_2})
        final class SampleClass {
        }
        final NdmCollectionAllowedStrings allowedStrings = SampleClass.class.getAnnotation(NdmCollectionAllowedStrings.class);
        assertDoesNotThrow(() -> cut.initialize(allowedStrings), "exception should not occur during initialization");
    }

    @Test
    void whenAllCollectionValuesAreInAllowedValuesThenItIsConsideredValid() {
        assertTrue(cut.isValid(Set.of(ALLOWED, ALSO_ALLOWED), mockConstraintValidatorContext), "all collection values are in allowed values, should be valid");
    }

    @Test
    void whenCollectionValueIsNotInAllowedValuesThenItIsConsideredNotValid() {
        assertFalse(cut.isValid(Set.of(ALLOWED, ALLOWED_1), mockConstraintValidatorContext), "collection values is not in allowed values, should not be valid");
    }

    @Test
    void whenCollectionValueIsNullThenItIsConsideredNotValid() {
        final Set<String> set = new HashSet<>();
        set.add(null);
        assertFalse(cut.isValid(set, mockConstraintValidatorContext), "collection value is null, should not be valid");
    }


    @Test
    void whenCollectionIsNullAndNullIsNotAllowedThenItIsConsideredNotValid() {
        assertFalse(cut.isValid(null, mockConstraintValidatorContext), "collection is null and null is not allowed, should not be valid");
    }

    @Test
    void whenCollectionIsNullAndNullIsAllowedThenItIsConsideredValid() {
        @NdmCollectionAllowedStrings(nullAllowed = true, values = {ALLOWED, ALSO_ALLOWED})
        final class SampleClass {
        }
        final NdmCollectionAllowedStrings allowedStrings = SampleClass.class.getAnnotation(NdmCollectionAllowedStrings.class);
        cut.initialize(allowedStrings);
        assertTrue(cut.isValid(null, mockConstraintValidatorContext), "collection is null and null is allowed, should be valid");
    }

    @Test
    void whenCollectionIsEmptyAndEmptyIsNotAllowedThenItIsConsideredNotValid() {
        assertFalse(cut.isValid(Set.of(), mockConstraintValidatorContext), "collection is empty and empty is not allowed, should not be valid");
    }

    @Test
    void whenCollectionIsEmptyAndEmptyIsAllowedThenItIsConsideredValid() {
        @NdmCollectionAllowedStrings(emptyAllowed = true, values = {ALLOWED, ALSO_ALLOWED})
        final class SampleClass {
        }
        final NdmCollectionAllowedStrings allowedStrings = SampleClass.class.getAnnotation(NdmCollectionAllowedStrings.class);
        cut.initialize(allowedStrings);
        assertTrue(cut.isValid(Set.of(), mockConstraintValidatorContext), "collection is empty and empty is allowed, should be valid");
    }
}
