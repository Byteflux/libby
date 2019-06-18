package net.byteflux.libby.logging;

import net.byteflux.libby.logging.adapters.LogAdapter;

import static java.util.Objects.requireNonNull;

/**
 * A logging wrapper that logs to a log adapter and can be configured to filter
 * log messages by severity.
 */
public class Logger {
    /**
     * Log adapter for the current platform
     */
    private final LogAdapter adapter;

    /**
     * Log level controlling which messages are logged
     */
    private LogLevel level = LogLevel.INFO;

    /**
     * Creates a new logger with the provided adapter.
     *
     * @param adapter the adapter to wrap
     */
    public Logger(LogAdapter adapter) {
        this.adapter = requireNonNull(adapter, "adapter");
    }

    /**
     * Gets the current log level.
     *
     * @return current log level
     */
    public LogLevel getLevel() {
        return level;
    }

    /**
     * Sets a new log level.
     *
     * @param level new log level
     */
    public void setLevel(LogLevel level) {
        this.level = requireNonNull(level, "level");
    }

    /**
     * Gets whether messages matching the provided level can be logged under
     * the current log level setting.
     * <p>
     * Returns true if provided log level is equal to or more severe than the
     * logger's configured log level.
     *
     * @param level the level to check
     * @return true if message can be logged, or false
     */
    private boolean canLog(LogLevel level) {
        return requireNonNull(level, "level").compareTo(this.level) >= 0;
    }

    /**
     * Logs a message with the provided level.
     * <p>
     * If the provided log level is less severe than the logger's
     * configured log level, this message won't be logged.
     *
     * @param level   message severity level
     * @param message the message to log
     * @see #debug(String)
     * @see #info(String)
     * @see #warn(String)
     * @see #error(String)
     */
    public void log(LogLevel level, String message) {
        if (canLog(level)) {
            adapter.log(level, message);
        }
    }

    /**
     * Logs a message and stack trace with the provided level.
     * <p>
     * If the provided log level is less severe than the logger's
     * configured log level, this message won't be logged.
     *
     * @param level     message severity level
     * @param message   the message to log
     * @param throwable the throwable to print
     * @see #debug(String, Throwable)
     * @see #info(String, Throwable)
     * @see #warn(String, Throwable)
     * @see #error(String, Throwable)
     */
    public void log(LogLevel level, String message, Throwable throwable) {
        if (canLog(level)) {
            adapter.log(level, message, throwable);
        }
    }

    /**
     * Logs a debug message.
     * <p>
     * If the logger's configured log level is more severe than
     * {@link LogLevel#DEBUG}, this message won't be logged.
     *
     * @param message the message to log
     */
    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    /**
     * Logs a debug message with a stack trace.
     * <p>
     * If the logger's configured log level is more severe than
     * {@link LogLevel#DEBUG}, this message won't be logged.
     *
     * @param message   the message to log
     * @param throwable the throwable to print
     */
    public void debug(String message, Throwable throwable) {
        log(LogLevel.DEBUG, message, throwable);
    }

    /**
     * Logs an informational message.
     * <p>
     * If the logger's configured log level is more severe than
     * {@link LogLevel#INFO}, this message won't be logged.
     *
     * @param message the message to log
     */
    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    /**
     * Logs an informational message with a stack trace.
     * <p>
     * If the logger's configured log level is more severe than
     * {@link LogLevel#INFO}, this message won't be logged.
     *
     * @param message   the message to log
     * @param throwable the throwable to print
     */
    public void info(String message, Throwable throwable) {
        log(LogLevel.INFO, message, throwable);
    }

    /**
     * Logs a warning message.
     * <p>
     * If the logger's configured log level is more severe than
     * {@link LogLevel#WARN}, this message won't be logged.
     *
     * @param message the message to log
     */
    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    /**
     * Logs a warning message with a stack trace.
     * <p>
     * If the logger's configured log level is more severe than
     * {@link LogLevel#WARN}, this message won't be logged.
     *
     * @param message   the message to log
     * @param throwable the throwable to print
     */
    public void warn(String message, Throwable throwable) {
        log(LogLevel.WARN, message, throwable);
    }

    /**
     * Logs an error message.
     *
     * @param message the message to log
     */
    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    /**
     * Logs an error message with a stack trace.
     *
     * @param message   message to log
     * @param throwable the throwable to print
     */
    public void error(String message, Throwable throwable) {
        log(LogLevel.ERROR, message, throwable);
    }
}
