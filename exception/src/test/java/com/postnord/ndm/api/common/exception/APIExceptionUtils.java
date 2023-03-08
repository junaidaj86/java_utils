package com.postnord.ndm.api.common.exception;

import java.net.URI;
import java.util.Map;

/**
 * The base class for testing the <code>APIException</code> class. Provides parameterized test data for the various constructors.
 */
class APIExceptionUtils {

    protected static class TestData {

        private final URI type;
        private final String title;
        private final int statusCode;
        private final String detail;
        private final URI instance;
        private final String messagePart;
        private final Map<String, Object> parameters;

        TestData(final String messagePart, final URI type, final String title, final int statusCode, final String detail,
                 final URI instance, final Map<String, Object> parameters) {
            this.type = type;
            this.title = title;
            this.statusCode = statusCode;
            this.detail = detail;
            this.instance = instance;
            this.messagePart = messagePart;
            this.parameters = parameters;
        }

        TestData(final String messagePart, final URI type, final String title, final int statusCode,
                 final String detail, final URI instance) {
            this(messagePart, type, title, statusCode, detail, instance, null);
        }

        TestData(final String messagePart, final URI type, final String title, final int statusCode, final String detail) {
            this(messagePart, type, title, statusCode, detail, null);
        }

        TestData(final String messagePart, final String title, final int statusCode, final String detail) {
            this(messagePart, null, title, statusCode, detail, null);
        }

        TestData(final String messagePart, final URI type, final String title, final int statusCode) {
            this(messagePart, type, title, statusCode, null);
        }


        String getMessagePart() {
            return messagePart;
        }

        URI getType() {
            return type;
        }

        URI getInstance() {
            return instance;
        }

        String getTitle() {
            return title;
        }

        int getStatusCode() {
            return statusCode;
        }

        String getDetail() {
            return detail;
        }

        Map<String, Object> getParameters() {
            return parameters;
        }

        @Override
        public String toString() {
            return "{'" + messagePart + "'} is null";
        }
    }
}
