package net.byteflux.libby.classloader;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

/**
 * A reflection-based wrapper around {@link URLClassLoader} for adding URLs to
 * the classpath.
 */
public class URLClassLoaderHelper {
    /**
     * The class loader being managed by this helper.
     */
    private final URLClassLoader classLoader;

    /**
     * A reflected method in {@link URLClassLoader}, when invoked adds a URL
     * to the classpath
     */
    private final Method addURLMethod;

    /**
     * Creates a new URL class loader helper.
     *
     * @param classLoader the class loader to manage
     */
    public URLClassLoaderHelper(URLClassLoader classLoader) {
        this.classLoader = requireNonNull(classLoader, "classLoader");

        try {
            addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            addURLMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a URL to the class loader's classpath.
     *
     * @param url the URL to add
     */
    public void addToClasspath(URL url) {
        try {
            addURLMethod.invoke(classLoader, requireNonNull(url, "url"));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a path to the class loader's classpath.
     *
     * @param path the path to add
     */
    public void addToClasspath(Path path) {
        try {
            addToClasspath(requireNonNull(path, "path").toUri().toURL());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
