package net.byteflux.libby;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.PluginManager;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import net.byteflux.libby.logging.adapters.SLF4JLogAdapter;
import org.slf4j.Logger;

import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

/**
 * A runtime dependency manager for Velocity plugins.
 */
public class VelocityLibraryManager<T> extends LibraryManager {
    /**
     * Velocity plugin manager used for adding files to the plugin's classpath
     */
    private final PluginManager pluginManager;

    /**
     * The plugin instance required by the plugin manager to add files to the
     * plugin's classpath
     */
    private final T plugin;

    /**
     * Creates a new Velocity library manager.
     *
     * @param logger        the plugin logger
     * @param dataDirectory plugin's data directory
     * @param pluginManager Velocity plugin manager
     * @param plugin        the plugin to manage
     */
    @Inject
    private VelocityLibraryManager(Logger logger,
                                   @DataDirectory Path dataDirectory,
                                   PluginManager pluginManager,
                                   T plugin) {

        super(new SLF4JLogAdapter(logger), dataDirectory);
        this.pluginManager = requireNonNull(pluginManager, "pluginManager");
        this.plugin = requireNonNull(plugin, "plugin");
    }

    /**
     * Adds a file to the Velocity plugin's classpath.
     *
     * @param file the file to add
     */
    @Override
    protected void addToClasspath(Path file) {
        pluginManager.addToClasspath(plugin, file);
    }
}
