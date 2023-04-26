package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmDynamicList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import jakarta.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
 class NdmDynamicListValidatorTest {
    private NdmDynamicListValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {

        @NdmDynamicList
        final class SampleClass {
        }
        final NdmDynamicList iotaDynamicList = SampleClass.class.getAnnotation(NdmDynamicList.class);
        cut = new NdmStaticListValidator();
        cut.initialize(iotaDynamicList);
    }

    @Test
     void whenListContainsValueThenItIsValid() {


        assertTrue(cut.isValid("en", mockConstraintValidatorContext),
                "");
    }

    @Test
     void whenListContainsValueWithUnderscoreThenItIsValid() {


        assertTrue(cut.isValid("in_ID", mockConstraintValidatorContext),
                "");
    }

    @Test
     void whenListContainsValueThenItNotValid() {


        assertFalse(cut.isValid("en_uk", mockConstraintValidatorContext),
                "Values not allowed.");
    }

    @Test
     void whenBlankValueCheckedListContainsValueThenItNotValid() {


        assertFalse(cut.isValid("", mockConstraintValidatorContext),
                "Values not allowed.");
    }

    @Test
     void whenNullValueCheckedListContainsValueThenItNotValid() {


        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "Values not allowed.");
    }


    class NdmStaticListValidator extends NdmDynamicListValidator {
        private boolean nullAllowed;
        // For test case using static list, here dynamic list will be populated.

        List<String> staticList = List.of("it", "in_ID", "fr", "es", "en", "de", "zh_TW", "zh_CN", "th", "ru", "pt_BR", "nl", "ja");


        @Override
        public void initialize(final NdmDynamicList mockConstraintValidatorContext) {
            nullAllowed = mockConstraintValidatorContext.nullAllowed();

        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext constraintContext) {

            if (value == null) {
                return nullAllowed;
            } else {
                return staticList.contains(value);
            }

        }

        @Override
        protected boolean validate(final String value) {
            return false;
        }
    } //inner class hack
}
