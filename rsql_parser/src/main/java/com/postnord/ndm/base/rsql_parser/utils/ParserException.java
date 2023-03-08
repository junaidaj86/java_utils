package com.postnord.ndm.base.rsql_parser.utils;

public class ParserException extends RuntimeException {
    public ParserException(final String message) {
        super(message);
    }

    public ParserException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
