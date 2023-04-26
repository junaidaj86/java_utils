package com.postnord.ndm.api.common.exception;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

public class APIException extends RuntimeException {

    public static final String UNSET = "UNSET";
    private final URI type;
    private final String title;
    private final int status;
    private final String detail;
    private final URI instance;
    private final transient Map<String, Object> parameters;

    public APIException() {
        this(URI.create(UNSET), UNSET, INTERNAL_SERVER_ERROR.getStatusCode(),
                UNSET, URI.create(UNSET), new LinkedHashMap<>());
    }

    public APIException(final URI type) {
        this(type, UNSET, INTERNAL_SERVER_ERROR.getStatusCode(), UNSET, URI.create(UNSET), new LinkedHashMap<>());
    }

    public APIException(final URI type, final String title) {

        this(type, title, INTERNAL_SERVER_ERROR.getStatusCode(), UNSET, URI.create(UNSET), new LinkedHashMap<>());
    }

    public APIException(final URI type, final String title, final int status) {
        this(type, title, status, UNSET, URI.create(UNSET), new LinkedHashMap<>());
    }

    public APIException(final URI type, final String title,
                        final int status, final String detail) {
        this(type, title, status, detail, URI.create(UNSET), new LinkedHashMap<>());
    }

    public APIException(final String title, final int status, final String detail) {
        this(URI.create(UNSET), title, status, detail, URI.create(UNSET), new LinkedHashMap<>());
    }

    public APIException(final URI type, final String title,
                        final int status, final String detail,
                        final URI instance) {
        this(type, title, status, detail, instance, new LinkedHashMap<>());

    }

    public APIException(final URI type, final String title,
                        final int status, final String detail,
                        final URI instance,
                        final Map<String, Object> parameters) {
        super();

        checkType(type);
        checkTitle(title);
        checkStatus(status);
        checkDetail(detail);
        checkInstance(instance);
        checkParameters(parameters);

        this.type = type;
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
        this.parameters = parameters;
    }

    private void checkParameters(final Map<String, Object> parameters) {
        if (parameters == null) {
            throw new APIException(BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.getStatusCode(),
                    "Illegal value in field: 'parameters', reason: mandatory field 'parameters' must be specified");
        }
    }

    private void checkInstance(final URI instance) {
        if (instance == null) {
            throw new APIException(BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.getStatusCode(),
                    "Illegal value in field: 'instance', reason: mandatory field 'instance' must be specified");
        }
    }

    private void checkDetail(final String detail) {
        if (detail == null) {
            throw new APIException(BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.getStatusCode(),
                    "Illegal value in field: 'detail', reason: mandatory field 'detail' must be specified");
        }
    }

    private void checkStatus(final int status) {
        if (status == 0) {
            throw new APIException(BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.getStatusCode(),
                    "Illegal value in field: 'status', reason: mandatory field 'status' must be specified");
        }
    }

    private void checkTitle(final String title) {
        if (title == null) {
            throw new APIException(BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.getStatusCode(),
                    "Illegal value in field: 'title', reason: mandatory field 'title' must be specified");
        }
    }

    private void checkType(final URI type) {
        if (type == null) {
            throw new APIException(BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.getStatusCode(),
                    "Illegal value in field: 'type', reason: mandatory field 'type' must be specified");
        }
    }

    public URI getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public int getStatus() {
        return status;
    }

    public String getDetail() {
        return detail;
    }

    public URI getInstance() {
        return instance;
    }

    public Map<String, Object> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    @Override
    public String getMessage() {
        return Stream.of(getTitle(), getDetail())
                .filter(Objects::nonNull)
                .collect(joining(": "));
    }

    @Override
    public String getLocalizedMessage() {
        return "";
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return new StackTraceElement[0];
    }

    @Override
    public void setStackTrace(final StackTraceElement[] stackTrace) {
        super.setStackTrace(new StackTraceElement[0]);
    }

    @Override
    @SuppressWarnings("PMD.AvoidSynchronizedAtMethodLevel") //cannot remove PMD and SONAR not aligned
    public synchronized Throwable getCause() {
        return null;
    }

    @Override
    public String toString() {
        return "APIException{" +
                "type=" + type +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", detail='" + detail + '\'' +
                ", instance=" + instance +
                ", parameters=" + parameters +
                '}';
    }
}
