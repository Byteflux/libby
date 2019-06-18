package net.byteflux.libby.logging;

/**
 * Represents the severity of a log message in {@link Logger}.
 */
public enum LogLevel {
    /**
     * Stuff that isn't useful to end-users
     */
    DEBUG,

    /**
     * Stuff that might be useful to know
     */
    INFO,

    /**
     * Non-fatal, often recoverable errors or notices
     */
    WARN,

    /**
     * Probably an unrecoverable error
     */
    ERROR
}
