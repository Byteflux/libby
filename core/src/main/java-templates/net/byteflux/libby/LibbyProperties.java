package net.byteflux.libby;

/**
 * Filtered Maven properties and other related constants.
 */
public class LibbyProperties {
    /**
     * Project version
     */
    public static final String VERSION = "${project.version}";

    /**
     * User agent string to use when downloading libraries
     */
    public static final String HTTP_USER_AGENT = "libby/" + VERSION;
}
