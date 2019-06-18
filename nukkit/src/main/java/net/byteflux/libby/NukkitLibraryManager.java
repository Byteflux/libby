package net.byteflux.libby;

import cn.nukkit.plugin.Plugin;
import net.byteflux.libby.classloader.URLClassLoaderHelper;
import net.byteflux.libby.logging.adapters.NukkitLogAdapter;

import java.net.URLClassLoader;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

/**
 * A runtime dependency manager for Nukkit plugins.
 */
public class NukkitLibraryManager extends LibraryManager {
    /**
     * Plugin classpath helper
     */
    private final URLClassLoaderHelper classLoader;

    /**
     * Creates a new Nukkit library manager.
     *
     * @param plugin the plugin to manage
     */
    public NukkitLibraryManager(Plugin plugin) {
        super(new NukkitLogAdapter(requireNonNull(plugin, "plugin").getLogger()), plugin.getDataFolder().toPath());
        classLoader = new URLClassLoaderHelper((URLClassLoader) plugin.getClass().getClassLoader());
    }

    /**
     * Adds a file to the Nukkit plugin's classpath.
     *
     * @param file the file to add
     */
    @Override
    protected void addToClasspath(Path file) {
        classLoader.addToClasspath(file);
    }
}
