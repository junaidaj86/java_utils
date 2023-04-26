package com.postnord.ndm.api.common.validation.validator;

import com.postnord.ndm.api.common.validation.constraints.NdmCollectionStringRegex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintValidatorContext;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NdmCollectionStringRegexValidatorTest {

    private NdmCollectionStringRegexValidator cut;
    @Mock
    private ConstraintValidatorContext mockConstraintValidatorContext;

    @BeforeEach
     void setUp() {
        cut = new NdmCollectionStringRegexValidator();
        @NdmCollectionStringRegex(regex = "^[\\w:._-]+$")
        final class SampleClass {
        }
        final NdmCollectionStringRegex iotaCollectionStringRegex = SampleClass.class.getAnnotation(NdmCollectionStringRegex.class);
        cut.initialize(iotaCollectionStringRegex);
    }

    @Test
    void whenValidatorIsInitializedThenAnnotationIsAccepted() {
        @NdmCollectionStringRegex()
        final class SampleClass {
        }
        final NdmCollectionStringRegex iotaCollectionStringRegex = SampleClass.class.getAnnotation(NdmCollectionStringRegex.class);
        assertDoesNotThrow(() -> cut.initialize(iotaCollectionStringRegex), "exception should not occur during initialization");
    }

    @Test
    void whenSingleItemOfCollectionMatchingRegexThenItShouldBeValid() {

        assertTrue(cut.isValid(Stream.of("unittest:valid1").collect(Collectors.toSet())
                        , mockConstraintValidatorContext),
                "Collection not valid single item not matching regex");
    }

    @Test
    void whenSingleItemOfCollectionNotMatchingRegexThenItShouldNotBeValid() {

        assertFalse(cut.isValid(Stream.of("STUPID@£$:1").collect(Collectors.toSet())
                        , mockConstraintValidatorContext),
                "Collection not valid single item with special characters not matching regex");
    }

    @Test
    void whenMultipleItemsOfCollectionMatchingRegexThenItShouldBeValid() {

        assertTrue(cut.isValid(Stream.of("unittest:value1", "unitTest:-", "unitTest:-.1").collect(Collectors.toSet())
                        , mockConstraintValidatorContext),
                "Collection not valid multiple items not matching regex");
    }

    @Test
    void whenMultipleItemsOfCollectionStartsWithNotMatchingRegexThenItShouldNotBeValid() {

        assertFalse(cut.isValid(Stream.of("InvalidRegex:@£$", "ValidRegex:unitTest-").collect(Collectors.toSet())
                        , mockConstraintValidatorContext),
                "Collection not valid single item at start not matching regex");
    }

    @Test
    void whenMultipleItemsOfCollectionMatchingAnotherTypeRegexThenItShouldBeValid() {

        @NdmCollectionStringRegex(regex = "^[1-9][0-9]{0,17}$")
        final class SampleClass {
        }
        final NdmCollectionStringRegex iotaCollectionStringRegex = SampleClass.class.getAnnotation(NdmCollectionStringRegex.class);
        cut.initialize(iotaCollectionStringRegex);

        assertTrue(cut.isValid(Stream.of("19", "2", "4", "5", "6", "12345678").collect(Collectors.toSet())
                        , mockConstraintValidatorContext),
                "Collection contains invalid item not matching regex");
    }

    @Test
    void whenMultipleItemsOfCollectionMatchingAnotherTypeRegexThenItShouldNotBeValid() {

        @NdmCollectionStringRegex(regex = "^[1-9][0-9]{0,17}$")
        final class SampleClass {
        }
        final NdmCollectionStringRegex iotaCollectionStringRegex = SampleClass.class.getAnnotation(NdmCollectionStringRegex.class);
        cut.initialize(iotaCollectionStringRegex);

        assertFalse(cut.isValid(Stream.of("19", "ab", "4", "5", "6", "12345678").collect(Collectors.toSet())
                        , mockConstraintValidatorContext),
                "Collection contains invalid item not matching regex");
    }

    @Test
    void whenMultipleItemsOfCollectionEndsWithNotMatchingRegexThenItShouldNotBeValid() {

        assertFalse(cut.isValid(Stream.of("ValidRegex:unitTest-1", "InvalidRegex:@£$[]").collect(Collectors.toSet())
                        , mockConstraintValidatorContext),
                "Collection not valid single item at end not matching regex");
    }

    @Test
    void whenSomeOfMultipleItemsOfCollectionAreNotMatchingRegexThenItShouldNotBeValid() {

        assertFalse(cut.isValid(Stream.of("ValidRegex:unit_Test-1", "ValidRegex:unit.Case", "InvalidRegex:+", "ValidRegex:.-_").collect(Collectors.toSet())
                        , mockConstraintValidatorContext),
                "Collection not valid multiple items not matching regex");
    }

    @Test
    void whenCollectionItemHasSpaceThenItShouldNotBeValid() {

        assertFalse(cut.isValid(Stream.of(" ").collect(Collectors.toSet())
                        , mockConstraintValidatorContext),
                "Collection contains string with only space, should be invalid");
    }

    @Test
    void whenCollectionItemEndsWithSpaceThenItShouldNotBeValid() {

        assertFalse(cut.isValid(Stream.of("tagValue ").collect(Collectors.toSet())
                        , mockConstraintValidatorContext),
                "Collection contains string with space at end, should be invalid");
    }

    @Test
    void whenCollectionItemStartsWithSpaceThenItShouldNotBeValid() {

        assertFalse(cut.isValid(Stream.of(" tagValue").collect(Collectors.toSet())
                        , mockConstraintValidatorContext),
                "Collection contains string with space at front, should be invalid");
    }

    @Test
    void whenCollectionItemContainsSpaceBetweenLettersThenItShouldNotBeValid() {

        assertFalse(cut.isValid(Stream.of("tagValue         tagValue").collect(Collectors.toSet())
                        , mockConstraintValidatorContext),
                "Collection contains string with space between, should be invalid");
    }

    @Test
    void whenMultipleItemsOfCollectionAreMatchingRegexWithNewConfiguredValidValueThenItShouldBeValid() {

        @NdmCollectionStringRegex(regex = "^[#$@]+$")
        final class SampleClass {
        }
        final NdmCollectionStringRegex iotaCollectionStringRegex = SampleClass.class.getAnnotation(NdmCollectionStringRegex.class);
        cut.initialize(iotaCollectionStringRegex);

        assertTrue(cut.isValid(Stream.of("@", "#$@", "#$", "$").collect(Collectors.toSet())
                        , mockConstraintValidatorContext),
                "Collection contains invalid item with regex characters not matching regex");
    }

    @Test
    void whenMultipleItemsOfCollectionAreNotMatchingRegexWithNewConfiguredValidValueThenItShouldNotBeValid() {

        @NdmCollectionStringRegex(regex = "^[#$@]+$")
        final class SampleClass {
        }
        final NdmCollectionStringRegex iotaCollectionStringRegex = SampleClass.class.getAnnotation(NdmCollectionStringRegex.class);
        cut.initialize(iotaCollectionStringRegex);

        assertFalse(cut.isValid(Stream.of("1234", "unit_Test-").collect(Collectors.toSet())
                        , mockConstraintValidatorContext),
                "Collection contains invalid item not matching regex");
    }

    @Test
    void whenCollectionIsFilledWithSingleBlankItemThenItShouldNotBeValid() {

        assertFalse(cut.isValid(Stream.of("").collect(Collectors.toSet())
                        , mockConstraintValidatorContext),
                "Collection contains an empty string value");
    }

    @Test
    void whenCollectionIsEmptyThenItShouldBeValid() {

        assertTrue(cut.isValid(Collections.emptySet(), mockConstraintValidatorContext),
                "Collection is emptySet, should be fine");
    }

    @Test
    void whenCollectionIsNullAndNullNotAllowedThenItShouldNotBeValid() {

        assertFalse(cut.isValid(null, mockConstraintValidatorContext),
                "Collection is null, should not be null");
    }

    @Test
    void whenCollectionIsNullAndNullAllowedThenItIsConsideredValid() {
        @NdmCollectionStringRegex(nullAllowed = true, regex = "^[#$x]+$")
        final class SampleClass {
        }
        final NdmCollectionStringRegex iotaCollectionStringRegex = SampleClass.class.getAnnotation(NdmCollectionStringRegex.class);
        cut.initialize(iotaCollectionStringRegex);

        assertTrue(cut.isValid(null, mockConstraintValidatorContext),
                "collection is null and null is allowed, should be valid");
    }

}
