package net.byteflux.libby;

import com.google.inject.Inject;
import net.byteflux.libby.classloader.URLClassLoaderHelper;
import net.byteflux.libby.logging.adapters.SLF4JLogAdapter;
import org.slf4j.Logger;
import org.spongepowered.api.config.ConfigDir;

import java.net.URLClassLoader;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

/**
 * A runtime dependency manager for Sponge plugins.
 */
public class SpongeLibraryManager<T> extends LibraryManager {
    /**
     * Plugin classpath helper
     */
    private final URLClassLoaderHelper classLoader;

    /**
     * Creates a new Sponge library manager.
     *
     * @param logger        the plugin logger
     * @param dataDirectory plugin's data directory
     * @param plugin        the plugin to manage
     */
    @Inject
    private SpongeLibraryManager(Logger logger, @ConfigDir(sharedRoot = false) Path dataDirectory, T plugin) {
        super(new SLF4JLogAdapter(logger), dataDirectory);
        classLoader = new URLClassLoaderHelper((URLClassLoader) requireNonNull(plugin, "plugin").getClass().getClassLoader());
    }

    /**
     * Adds a file to the Sponge plugin's classpath.
     *
     * @param file the file to add
     */
    @Override
    protected void addToClasspath(Path file) {
        classLoader.addToClasspath(file);
    }
}
