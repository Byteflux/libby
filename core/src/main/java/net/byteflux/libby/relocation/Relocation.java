package net.byteflux.libby.relocation;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import static java.util.Objects.requireNonNull;

/**
 * Relocations are used to describe a search and replace pattern for renaming
 * packages in a library jar for the purpose of preventing namespace conflicts
 * with other plugins that bundle their own version of the same library.
 */
public class Relocation {
    /**
     * Search pattern
     */
    private final String pattern;

    /**
     * Replacement pattern
     */
    private final String relocatedPattern;

    /**
     * Classes and resources to include
     */
    private final Collection<String> includes;

    /**
     * Classes and resources to exclude
     */
    private final Collection<String> excludes;

    /**
     * Creates a new relocation.
     *
     * @param pattern          search pattern
     * @param relocatedPattern replacement pattern
     * @param includes         classes and resources to include
     * @param excludes         classes and resources to exclude
     */
    public Relocation(String pattern, String relocatedPattern, Collection<String> includes, Collection<String> excludes) {
        this.pattern = requireNonNull(pattern, "pattern").replace("{}", ".");
        this.relocatedPattern = requireNonNull(relocatedPattern, "relocatedPattern").replace("{}", ".");
        this.includes = includes != null ? Collections.unmodifiableList(new LinkedList<>(includes)) : Collections.emptyList();
        this.excludes = excludes != null ? Collections.unmodifiableList(new LinkedList<>(excludes)) : Collections.emptyList();
    }

    /**
     * Creates a new relocation with empty includes and excludes.
     *
     * @param pattern          search pattern
     * @param relocatedPattern replacement pattern
     */
    public Relocation(String pattern, String relocatedPattern) {
        this(pattern, relocatedPattern, null, null);
    }

    /**
     * Gets the search pattern.
     *
     * @return pattern to search
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Gets the replacement pattern.
     *
     * @return pattern to replace with
     */
    public String getRelocatedPattern() {
        return relocatedPattern;
    }

    /**
     * Gets included classes and resources.
     *
     * @return classes and resources to include
     */
    public Collection<String> getIncludes() {
        return includes;
    }

    /**
     * Gets excluded classes and resources.
     *
     * @return classes and resources to exclude
     */
    public Collection<String> getExcludes() {
        return excludes;
    }

    /**
     * Creates a new relocation builder.
     *
     * @return new relocation builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Provides an alternative method of creating a {@link Relocation}. This
     * builder may be more intuitive for configuring relocations that also have
     * any includes or excludes.
     */
    public static class Builder {
        /**
         * Search pattern
         */
        private String pattern;

        /**
         * Replacement pattern
         */
        private String relocatedPattern;

        /**
         * Classes and resources to include
         */
        private final Collection<String> includes = new LinkedList<>();

        /**
         * Classes and resources to exclude
         */
        private final Collection<String> excludes = new LinkedList<>();

        /**
         * Sets the search pattern.
         *
         * @param pattern pattern to search
         * @return this builder
         */
        public Builder pattern(String pattern) {
            this.pattern = requireNonNull(pattern, "pattern");
            return this;
        }

        /**
         * Sets the replacement pattern.
         *
         * @param relocatedPattern pattern to replace with
         * @return this builder
         */
        public Builder relocatedPattern(String relocatedPattern) {
            this.relocatedPattern = requireNonNull(relocatedPattern, "relocatedPattern");
            return this;
        }

        /**
         * Adds a class or resource to be included.
         *
         * @param include class or resource to include
         * @return this builder
         */
        public Builder include(String include) {
            includes.add(requireNonNull(include, "include"));
            return this;
        }

        /**
         * Adds a class or resource to be excluded.
         *
         * @param exclude class or resource to exclude
         * @return this builder
         */
        public Builder exclude(String exclude) {
            excludes.add(requireNonNull(exclude, "exclude"));
            return this;
        }

        /**
         * Creates a new relocation using this builder's configuration.
         *
         * @return new relocation
         */
        public Relocation build() {
            return new Relocation(pattern, relocatedPattern, includes, excludes);
        }
    }
}
