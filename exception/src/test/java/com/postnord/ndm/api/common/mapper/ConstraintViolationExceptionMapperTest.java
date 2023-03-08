package com.postnord.ndm.api.common.mapper;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.metadata.ConstraintDescriptor;
import javax.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.TooManyStaticImports"})
@QuarkusTest
class ConstraintViolationExceptionMapperTest {

    public static final String STATUS_NOT_AS_EXPECTED = "status not as expected";
    private static final String MESSAGE = "message";
    private static final String ERROR_CODE = "errorCode";
    private static final String CONSTRAINT_CUSTOM_VIOLATION_MESSAGE = "Bad Request";
    private static final String MOCK_CODE = "2000";
    private final ConstraintViolation mockConstraintsViolation = mock(ConstraintViolation.class);
    @SuppressWarnings("unchecked")
    private final Set<ConstraintViolation<?>> mockViolations = (Set<ConstraintViolation<?>>) mock(Set.class);
    private final ConstraintViolationException mockException = mock(ConstraintViolationException.class);

    @Inject
    ConstraintViolationExceptionMapper constraintViolationExceptionMapper;

    @Test
    void whenMapperTranslatesFromConstraintViolationExceptionThenResponseContainsCorrectStatusCode() {

        mockNormalValidationExceptionThatHasConstraintViolations();

        try (Response response = constraintViolationExceptionMapper.toResponse(mockException)) {
            assertNotNull(response, "response is null");
            assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode(), STATUS_NOT_AS_EXPECTED);
        }
    }

    @Test
    void whenMapperTranslatesFromConstraintViolationExceptionThenResponseContainsCorrectMessage() {

        mockNormalValidationExceptionThatHasConstraintViolationsWithMockConstraintMessage();

        try (Response response = constraintViolationExceptionMapper.toResponse(mockException)) {
            assertNotNull(response, "reasponse is null");
            assertEquals(response.getStatus(), Response.Status.BAD_REQUEST.getStatusCode(), STATUS_NOT_AS_EXPECTED);

            assertTrue(response.getEntity().toString().contains(Response.Status.BAD_REQUEST.getReasonPhrase()),
                    "entity not as expected");
        }
    }

    @Test
    void whenMapperTranslatesFromConstraintViolationExceptionContentEmptyThenResponseHasCorrectStatusCodeErrorCodeAndMessage() {

        mockEmptyConstraintViolations();

        try (Response response = constraintViolationExceptionMapper.toResponse(mockException)) {
            assertNotNull(response, "response is null");
            assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus(), STATUS_NOT_AS_EXPECTED);
            assertTrue(response.getEntity().toString().contains(String.valueOf(Response.Status.BAD_REQUEST.getStatusCode())),
                    "status in entity not as expected");
            assertTrue(response.getEntity().toString().contains("Violation Unknown"), "Incorrect message returned");
        }
    }

    @SuppressWarnings("unchecked")
    private void mockEmptyConstraintViolations() {
        when(mockException.getConstraintViolations()).thenReturn(mockViolations);

        final Iterator<ConstraintViolation<?>> mockIterator = mock(Iterator.class);
        when(mockViolations.iterator()).thenReturn(mockIterator);

        when(mockIterator.hasNext()).thenReturn(false);
    }

    @SuppressWarnings("unchecked")
    private void mockNormalValidationExceptionThatHasConstraintViolations() {
        final String mockMessage = "some message";

        when(mockException.getConstraintViolations()).thenReturn(mockViolations);

        final ConstraintDescriptor mockDescriptor = mock(ConstraintDescriptor.class);
        final Map<String, Object> mockAttributes = mock(Map.class);
        when(mockConstraintsViolation.getConstraintDescriptor()).thenReturn(mockDescriptor);
        when(mockDescriptor.getAttributes()).thenReturn(mockAttributes);
        when(mockAttributes.get(MESSAGE)).thenReturn(mockMessage);
        when(mockAttributes.get(ERROR_CODE)).thenReturn(MOCK_CODE);

        final Iterator<ConstraintViolation<?>> mockIterator = mock(Iterator.class);
        when(mockViolations.iterator()).thenReturn(mockIterator);

        when(mockIterator.hasNext()).thenReturn(true);
        when(mockIterator.next()).thenReturn(mockConstraintsViolation);
    }

    @SuppressWarnings("unchecked")
    private void mockNormalValidationExceptionThatHasConstraintViolationsWithMockConstraintMessage() {
        when(mockException.getConstraintViolations()).thenReturn(mockViolations);

        final ConstraintDescriptor mockDescriptor = mock(ConstraintDescriptor.class);
        final Map<String, Object> mockAttributes = mock(Map.class);
        when(mockConstraintsViolation.getConstraintDescriptor()).thenReturn(mockDescriptor);
        when(mockDescriptor.getAttributes()).thenReturn(mockAttributes);
        when(mockConstraintsViolation.getMessage()).thenReturn(CONSTRAINT_CUSTOM_VIOLATION_MESSAGE);
        when(mockAttributes.get(ERROR_CODE)).thenReturn(MOCK_CODE);

        final Iterator<ConstraintViolation<?>> mockIterator = mock(Iterator.class);
        when(mockViolations.iterator()).thenReturn(mockIterator);

        when(mockIterator.hasNext()).thenReturn(true);
        when(mockIterator.next()).thenReturn(mockConstraintsViolation);
    }
}
