package com.postnord.ndm.api.common.problem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProblemTest {
    private static final String UNSET = "UNSET";
    private static final URI DEFAULT_TYPE = URI.create(UNSET);
    private static final URI PROBLEM_TYPE = URI.create("http://ndm.postnord.com/problems/404");
    private static final URI DEFAULT_INSTANCE = URI.create(UNSET);
    private static final URI PROBLEM_INSTANCE = URI.create("http://ndm.postnord.com/errors/404");
    private static final String DEFAULT_TITLE = UNSET;
    private static final String PROBLEM_TITLE = "NOT_FOUND";
    private static final String DEFAULT_DETAIL = UNSET;
    private static final String PROBLEM_DETAIL = "ASSET NOT FOUND";

    private static final int DEFAULT_STATUS = 0;
    private static final int PROBLEM_STATUS = 404;
    private static final Map<String, Object> DEFAULT_PARAMETERS = Collections.emptyMap();
    private static final Map<String, Object> PROBLEM_PARAMETERS = Map.of("param1", "value1", "param2", "value2");
    private static final Map<String, Object> PROBLEM_PARAMETERS_MIXED = Map.of("x", "y", "param1", "value1", "param2", "value2");

    @Test
    void whenProblemBuilderIsBuiltWithNoPropertiesThenProblemDefaultsCorrectly() {

        final Problem problem = Problem.builder().build();

        assertAll("Problem was not built with correct default properties",
                () -> assertNotNull(problem),
                () -> assertEquals(DEFAULT_TYPE, problem.getType()),
                () -> assertEquals(DEFAULT_INSTANCE, problem.getInstance()),
                () -> assertEquals(DEFAULT_TITLE, problem.getTitle()),
                () -> assertEquals(DEFAULT_STATUS, problem.getStatus()),
                () -> assertEquals(DEFAULT_DETAIL, problem.getDetail()),
                () -> assertEquals(DEFAULT_PARAMETERS, problem.getParameters()));
    }

    @Test
    void whenProblemBuilderIsBuiltWithSomePropertiesThenProblemDefaultsCorrectly() {

        final Problem problem = Problem.builder()
                .type(PROBLEM_TYPE)
                .instance(PROBLEM_INSTANCE)
                .title(PROBLEM_TITLE)
                .status(PROBLEM_STATUS)
                .detail(PROBLEM_DETAIL)
                .build();

        assertAll("Problem was not built with correct default or specified properties",
                () -> assertNotNull(problem),
                () -> assertEquals(PROBLEM_TYPE, problem.getType()),
                () -> assertEquals(PROBLEM_INSTANCE, problem.getInstance()),
                () -> assertEquals(PROBLEM_TITLE, problem.getTitle()),
                () -> assertEquals(PROBLEM_STATUS, problem.getStatus()),
                () -> assertEquals(PROBLEM_DETAIL, problem.getDetail()),
                () -> assertEquals(DEFAULT_PARAMETERS, problem.getParameters()));
    }

    @Test
    void whenProblemBuilderIsBuiltWithSomeSingularParametersThenParametersAreSetCorrectly() {

        final Problem problem = Problem.builder()
                .type(PROBLEM_TYPE)
                .parameter("param1", "value1")
                .parameter("param2", "value2")
                .build();

        assertAll("Problem was not built with correct singular parameters",
                () -> assertNotNull(problem),
                () -> assertEquals(PROBLEM_TYPE, problem.getType()),
                () -> assertEquals(DEFAULT_INSTANCE, problem.getInstance()),
                () -> assertEquals(DEFAULT_TITLE, problem.getTitle()),
                () -> assertEquals(DEFAULT_STATUS, problem.getStatus()),
                () -> assertEquals(DEFAULT_DETAIL, problem.getDetail()),
                () -> assertEquals(PROBLEM_PARAMETERS, problem.getParameters()));
    }

    @Test
    void whenProblemBuilderIsBuiltWithMapParametersThenParametersAreSetCorrectly() {

        final Problem problem = Problem.builder()
                .type(PROBLEM_TYPE)
                .parameters(PROBLEM_PARAMETERS)
                .build();

        assertAll("Problem Built with correct default properties",
                () -> assertNotNull(problem),
                () -> assertEquals(PROBLEM_TYPE, problem.getType()),
                () -> assertEquals(DEFAULT_INSTANCE, problem.getInstance()),
                () -> assertEquals(DEFAULT_TITLE, problem.getTitle()),
                () -> assertEquals(DEFAULT_STATUS, problem.getStatus()),
                () -> assertEquals(DEFAULT_DETAIL, problem.getDetail()),
                () -> assertEquals(PROBLEM_PARAMETERS, problem.getParameters()));
    }

    @Test
    void whenProblemBuilderIsBuiltWithMapParametersMixedThenParametersAreSetCorrectly() {

        final Problem problem = Problem.builder()
                .type(PROBLEM_TYPE)
                .parameters(PROBLEM_PARAMETERS)
                .parameter("x", "y")
                .build();

        assertAll("Problem Built with correct default properties",
                () -> assertNotNull(problem),
                () -> assertEquals(PROBLEM_TYPE, problem.getType()),
                () -> assertEquals(DEFAULT_INSTANCE, problem.getInstance()),
                () -> assertEquals(DEFAULT_TITLE, problem.getTitle()),
                () -> assertEquals(DEFAULT_STATUS, problem.getStatus()),
                () -> assertEquals(DEFAULT_DETAIL, problem.getDetail()),
                () -> assertEquals(PROBLEM_PARAMETERS_MIXED, problem.getParameters()));
    }

    @Test
    void whenProblemIsBuiltWithConstructorThenParametersAreSetCorrectly() {
        //coverage booster
        final Problem problem = new Problem(
                PROBLEM_TYPE,
                PROBLEM_TITLE,
                PROBLEM_STATUS,
                PROBLEM_DETAIL,
                PROBLEM_INSTANCE,
                PROBLEM_PARAMETERS
        );

        assertAll("Problem was not built with correct default or specified properties",
                () -> assertNotNull(problem),
                () -> assertEquals(PROBLEM_TYPE, problem.getType()),
                () -> assertEquals(PROBLEM_INSTANCE, problem.getInstance()),
                () -> assertEquals(PROBLEM_TITLE, problem.getTitle()),
                () -> assertEquals(PROBLEM_STATUS, problem.getStatus()),
                () -> assertEquals(PROBLEM_DETAIL, problem.getDetail()));
    }

    @Test
    void whenProblemBuilderIsBuiltWithNullProblemTypeThenIllegalArgumentExceptionIsThrown() {

        assertThrows(IllegalArgumentException.class, () -> Problem.builder().type(null).build());
    }

    @Test
    void whenProblemBuilderIsBuiltWithNullProblemInstanceThenIllegalArgumentExceptionIsThrown() {

        assertThrows(IllegalArgumentException.class, () -> Problem.builder().instance(null).build());
    }

    @Test
    void whenProblemBuilderIsBuiltWithNullProblemTitleThenIllegalArgumentExceptionIsThrown() {

        assertThrows(IllegalArgumentException.class, () -> Problem.builder().title(null).build());
    }

    @Test
    void whenProblemBuilderIsBuiltWithNullProblemDetailThenIllegalArgumentExceptionIsThrown() {

        assertThrows(IllegalArgumentException.class, () -> Problem.builder().detail(null).build());
    }

    @Test
    void whenProblemBuilderIsBuiltWithNullProblemParametersThenNullPointerExceptionIsThrown() {

        assertThrows(IllegalArgumentException.class, () -> Problem.builder().parameters(null).build());
    }

    @Test
    void whenClientModifiesParametersOutsideOfBuilderThenUnsupportedOperationExceptionIsThrown() {

        final Problem problem = Problem.builder()
                .parameters(PROBLEM_PARAMETERS)
                .build();

        assertThrows(UnsupportedOperationException.class, () -> problem.getParameters().put("a", "b"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"type", "title", "status", "detail", "instance"})
    void whenClientParametersAreReservedThenIllegalArgumentExceptionIsThrown(final String key) {

        assertThrows(IllegalArgumentException.class, () -> Problem.builder().parameter(key, "").build());
    }

    @Test
    void whenToStringIsCalledThenStringContainsParameters() {

        final Problem problem = Problem.builder()
                .type(PROBLEM_TYPE)
                .instance(PROBLEM_INSTANCE)
                .title(PROBLEM_TITLE)
                .status(PROBLEM_STATUS)
                .detail(PROBLEM_DETAIL)
                .parameters(PROBLEM_PARAMETERS)
                .build();

        assertAll("Problem toString did not contain the expected properties",
                () -> assertNotNull(problem),
                () -> assertTrue(problem.toString().contains(PROBLEM_DETAIL)),
                () -> assertTrue(problem.toString().contains(PROBLEM_INSTANCE.toString())),
                () -> assertTrue(problem.toString().contains(PROBLEM_PARAMETERS.toString())),
                () -> assertTrue(problem.toString().contains(PROBLEM_TITLE)),
                () -> assertTrue(problem.toString().contains(PROBLEM_TYPE.toString())),
                () -> assertTrue(problem.toString().contains(String.valueOf(PROBLEM_STATUS)))
        );
    }

    @Test
    void whenToStringIsCalledWithClearParamsThenStringContainsParameters() {

        final Problem problem = Problem.builder()
                .type(PROBLEM_TYPE)
                .instance(PROBLEM_INSTANCE)
                .title(PROBLEM_TITLE)
                .status(PROBLEM_STATUS)
                .detail(PROBLEM_DETAIL)
                .parameters(PROBLEM_PARAMETERS)
                .clearParameters() //remove the parameters i set
                .build();

        assertAll("Problem toString did not contain the expected properties",
                () -> assertNotNull(problem),
                () -> assertTrue(problem.toString().contains(PROBLEM_DETAIL)),
                () -> assertTrue(problem.toString().contains(PROBLEM_INSTANCE.toString())),
                () -> assertFalse(problem.toString().contains(PROBLEM_PARAMETERS.toString())),
                () -> assertTrue(problem.toString().contains(PROBLEM_TITLE)),
                () -> assertTrue(problem.toString().contains(PROBLEM_TYPE.toString())),
                () -> assertTrue(problem.toString().contains(String.valueOf(PROBLEM_STATUS)))
        );
    }

    @Test
    void whenToStringIsCalledOnProblemBuilderThenStringContainsParameters() {

        final String problemBuilder = Problem.builder()
                .type(PROBLEM_TYPE)
                .instance(PROBLEM_INSTANCE)
                .title(PROBLEM_TITLE)
                .status(PROBLEM_STATUS)
                .detail(PROBLEM_DETAIL)
                .parameters(PROBLEM_PARAMETERS).toString();

        assertAll("Problem toString did not contain the expected properties",
                () -> assertNotNull(problemBuilder),
                () -> assertTrue(problemBuilder.contains(PROBLEM_TYPE.toString())),
                () -> assertTrue(problemBuilder.contains(PROBLEM_INSTANCE.toString())),
                () -> assertTrue(problemBuilder.contains(PROBLEM_TITLE)),
                () -> assertTrue(problemBuilder.contains(String.valueOf(PROBLEM_STATUS))),
                () -> assertTrue(problemBuilder.contains(PROBLEM_DETAIL)));
    }
}
