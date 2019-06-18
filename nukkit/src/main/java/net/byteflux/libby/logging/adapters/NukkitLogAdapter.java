package net.byteflux.libby.logging.adapters;

import cn.nukkit.plugin.PluginLogger;
import net.byteflux.libby.logging.LogLevel;

import static java.util.Objects.requireNonNull;

/**
 * Logging adapter that logs to a Nukkit plugin logger.
 */
public class NukkitLogAdapter implements LogAdapter {
    /**
     * Nukkit plugin logger
     */
    private final PluginLogger logger;

    /**
     * Creates a new Nukkit log adapter that logs to a {@link PluginLogger}.
     *
     * @param logger the plugin logger to wrap
     */
    public NukkitLogAdapter(PluginLogger logger) {
        this.logger = requireNonNull(logger, "logger");
    }

    /**
     * Logs a message with the provided level to the Nukkit plugin logger.
     *
     * @param level   message severity level
     * @param message the message to log
     */
    @Override
    public void log(LogLevel level, String message) {
        switch (requireNonNull(level, "level")) {
            case DEBUG:
                logger.debug(message);
                break;
            case INFO:
                logger.info(message);
                break;
            case WARN:
                logger.warning(message);
                break;
            case ERROR:
                logger.error(message);
                break;
        }
    }

    /**
     * Logs a message and stack trace with the provided level to the Nukkit
     * plugin logger.
     *
     * @param level     message severity level
     * @param message   the message to log
     * @param throwable the throwable to print
     */
    @Override
    public void log(LogLevel level, String message, Throwable throwable) {
        switch (requireNonNull(level, "level")) {
            case DEBUG:
                logger.debug(message, throwable);
                break;
            case INFO:
                logger.info(message, throwable);
                break;
            case WARN:
                logger.warning(message, throwable);
                break;
            case ERROR:
                logger.error(message, throwable);
                break;
        }
    }
}
