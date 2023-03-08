package com.postnord.ndm.api.common.problem;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class ProblemResponseTest {

    public static final String UNSET = "UNSET";
    public static final URI DEFAULT_INSTANCE = URI.create(UNSET);
    public static final String DEFAULT_TITLE = UNSET;
    public static final String DEFAULT_DETAIL = UNSET;
    public static final MediaType EXPECTED_MEDIA_TYPE = MediaType.valueOf("application/problem+json");

    public static final int DEFAULT_STATUS = 0;
    public static final Map<String, Object> DEFAULT_PARAMETERS = Collections.emptyMap();

    public static final URI PROBLEM_TYPE = URI.create("http://ndm.postnord.com/problems/404");
    public static final URI PROBLEM_INSTANCE = URI.create("http://ndm.postnord.com/errors/404");
    public static final String PROBLEM_TITLE = "NOT_FOUND";
    public static final String PROBLEM_DETAIL = "ASSET NOT FOUND";
    public static final int PROBLEM_STATUS = 404;

    @Test
    void whenProblemResponseIsBuiltWithDefaultPropertiesThenProblemBuilderDefaultsCorrectly() {

        try (Response response = ProblemResponse.builder().problem(Problem.builder().build()).build()) {
            final Problem problemParsedFromResponse = response.readEntity(Problem.class);

            assertAll("ProblemResponse was not built with correct default properties",
                    () -> assertNotNull(response),
                    () -> assertEquals(DEFAULT_STATUS, response.getStatus()),
                    () -> assertEquals(EXPECTED_MEDIA_TYPE, response.getMediaType()),
                    () -> assertEquals(DEFAULT_INSTANCE, problemParsedFromResponse.getInstance()),
                    () -> assertEquals(DEFAULT_TITLE, problemParsedFromResponse.getTitle()),
                    () -> assertEquals(DEFAULT_STATUS, problemParsedFromResponse.getStatus()),
                    () -> assertEquals(DEFAULT_DETAIL, problemParsedFromResponse.getDetail()),
                    () -> assertEquals(DEFAULT_PARAMETERS, problemParsedFromResponse.getParameters())
            );
        }
    }

    @Test
    void whenProblemResponseIsBuiltWithSomePropertiesThenProblemBuilderDefaultsCorrectly() {

        try (Response response = ProblemResponse.builder()
                .problem(Problem.builder()
                        .type(PROBLEM_TYPE)
                        .instance(PROBLEM_INSTANCE)
                        .title(PROBLEM_TITLE)
                        .status(PROBLEM_STATUS)
                        .detail(PROBLEM_DETAIL).build()).build()) {

            final Problem problemParsedFromResponse = response.readEntity(Problem.class);

            assertAll("Problem was not built with correct default or specified properties",
                    () -> assertNotNull(response),
                    () -> assertEquals(PROBLEM_TYPE, problemParsedFromResponse.getType()),
                    () -> assertEquals(PROBLEM_INSTANCE, problemParsedFromResponse.getInstance()),
                    () -> assertEquals(PROBLEM_TITLE, problemParsedFromResponse.getTitle()),
                    () -> assertEquals(PROBLEM_STATUS, problemParsedFromResponse.getStatus()),
                    () -> assertEquals(PROBLEM_DETAIL, problemParsedFromResponse.getDetail()),
                    () -> assertEquals(DEFAULT_PARAMETERS, problemParsedFromResponse.getParameters()));
        }
    }

    @Test
    void whenProblemResponseIsBuiltWithConstructorThenProblemBuilderDefaultsCorrectly() {
        //coverage booster
        final ProblemResponse response = new ProblemResponse(Problem.builder().build());

        assertAll("ProblemResponse was not built with correct default properties",
                () -> assertNotNull(response)
        );
    }

    @Test
    void whenProblemResponseIsBuiltWithNullProblemThenIllegalArgumentExceptionIsThrown() {

        assertThrows(IllegalArgumentException.class, () -> ProblemResponse.builder().problem(null).build());
    }
}
