package net.byteflux.libby;

import net.byteflux.libby.relocation.Relocation;

import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import static java.util.Objects.requireNonNull;

/**
 * An immutable representation of a Maven artifact that can be downloaded,
 * relocated and then loaded into a plugin's classpath at runtime.
 *
 * @see #builder()
 */
public class Library {
    /**
     * Direct download URLs for this library
     */
    private final Collection<String> urls;

    /**
     * Maven group ID
     */
    private final String groupId;

    /**
     * Maven artifact ID
     */
    private final String artifactId;

    /**
     * Artifact version
     */
    private final String version;

    /**
     * Artifact classifier
     */
    private final String classifier;

    /**
     * Binary SHA-256 checksum for this library's jar file
     */
    private final byte[] checksum;

    /**
     * Jar relocations to apply
     */
    private final Collection<Relocation> relocations;

    /**
     * Relative Maven path to this library's artifact
     */
    private final String path;

    /**
     * Relative path to this library's relocated jar
     */
    private final String relocatedPath;

    /**
     * Creates a new library.
     *
     * @param urls        direct download URLs
     * @param groupId     Maven group ID
     * @param artifactId  Maven artifact ID
     * @param version     artifact version
     * @param classifier  artifact classifier or null
     * @param checksum    binary SHA-256 checksum or null
     * @param relocations jar relocations or null
     */
    private Library(Collection<String> urls,
                    String groupId,
                    String artifactId,
                    String version,
                    String classifier,
                    byte[] checksum,
                    Collection<Relocation> relocations) {

        this.urls = urls != null ? Collections.unmodifiableList(new LinkedList<>(urls)) : Collections.emptyList();
        this.groupId = requireNonNull(groupId, "groupId").replace("{}", ".");
        this.artifactId = requireNonNull(artifactId, "artifactId");
        this.version = requireNonNull(version, "version");
        this.classifier = classifier;
        this.checksum = checksum;
        this.relocations = relocations != null ? Collections.unmodifiableList(new LinkedList<>(relocations)) : Collections.emptyList();

        String path = this.groupId.replace('.', '/') + '/' + artifactId + '/' + version + '/' + artifactId + '-' + version;
        if (hasClassifier()) {
            path += '-' + classifier;
        }

        this.path = path + ".jar";
        relocatedPath = hasRelocations() ? path + "-relocated.jar" : null;
    }

    /**
     * Gets the direct download URLs for this library.
     *
     * @return direct download URLs
     */
    public Collection<String> getUrls() {
        return urls;
    }

    /**
     * Gets the Maven group ID for this library.
     *
     * @return Maven group ID
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Gets the Maven artifact ID for this library.
     *
     * @return Maven artifact ID
     */
    public String getArtifactId() {
        return artifactId;
    }

    /**
     * Gets the artifact version for this library.
     *
     * @return artifact version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Gets the artifact classifier for this library.
     *
     * @return artifact classifier or null
     */
    public String getClassifier() {
        return classifier;
    }

    /**
     * Gets whether this library has an artifact classifier.
     *
     * @return true if library has classifier, false otherwise
     */
    public boolean hasClassifier() {
        return classifier != null;
    }

    /**
     * Gets the binary SHA-256 checksum of this library's jar file.
     *
     * @return checksum or null
     */
    public byte[] getChecksum() {
        return checksum;
    }

    /**
     * Gets whether this library has a checksum.
     *
     * @return true if library has checksum, false otherwise
     */
    public boolean hasChecksum() {
        return checksum != null;
    }

    /**
     * Gets the jar relocations to apply to this library.
     *
     * @return jar relocations to apply
     */
    public Collection<Relocation> getRelocations() {
        return relocations;
    }

    /**
     * Gets whether this library has any jar relocations.
     *
     * @return true if library has relocations, false otherwise
     */
    public boolean hasRelocations() {
        return !relocations.isEmpty();
    }

    /**
     * Gets the relative Maven path to this library's artifact.
     *
     * @return Maven path for this library
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets the relative path to this library's relocated jar.
     *
     * @return path to relocated artifact or null if has no relocations
     */
    public String getRelocatedPath() {
        return relocatedPath;
    }

    /**
     * Gets a concise, human-readable string representation of this library.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        String name = groupId + ':' + artifactId + ':' + version;
        if (hasClassifier()) {
            name += ':' + classifier;
        }

        return name;
    }

    /**
     * Creates a new library builder.
     *
     * @return new library builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Due to the constructor complexity of an immutable {@link Library},
     * instead this fluent builder is used to configure and then construct
     * a new library.
     */
    public static class Builder {
        /**
         * Direct download URLs for this library
         */
        private final Collection<String> urls = new LinkedList<>();

        /**
         * Maven group ID
         */
        private String groupId;

        /**
         * Maven artifact ID
         */
        private String artifactId;

        /**
         * Artifact version
         */
        private String version;

        /**
         * Artifact classifier
         */
        private String classifier;

        /**
         * Binary SHA-256 checksum for this library's jar file
         */
        private byte[] checksum;

        /**
         * Jar relocations to apply
         */
        private final Collection<Relocation> relocations = new LinkedList<>();

        /**
         * Adds a direct download URL for this library.
         *
         * @param url direct download URL
         * @return this builder
         */
        public Builder url(String url) {
            urls.add(requireNonNull(url, "url"));
            return this;
        }

        /**
         * Sets the Maven group ID for this library.
         *
         * @param groupId Maven group ID
         * @return this builder
         */
        public Builder groupId(String groupId) {
            this.groupId = requireNonNull(groupId, "groupId");
            return this;
        }

        /**
         * Sets the Maven artifact ID for this library.
         *
         * @param artifactId Maven artifact ID
         * @return this builder
         */
        public Builder artifactId(String artifactId) {
            this.artifactId = requireNonNull(artifactId, "artifactId");
            return this;
        }

        /**
         * Sets the artifact version for this library.
         *
         * @param version artifact version
         * @return this builder
         */
        public Builder version(String version) {
            this.version = requireNonNull(version, "version");
            return this;
        }

        /**
         * Sets the artifact classifier for this library.
         *
         * @param classifier artifact classifier
         * @return this builder
         */
        public Builder classifier(String classifier) {
            this.classifier = requireNonNull(classifier, "classifier");
            return this;
        }

        /**
         * Sets the binary SHA-256 checksum for this library.
         *
         * @param checksum binary SHA-256 checksum
         * @return this builder
         */
        public Builder checksum(byte[] checksum) {
            this.checksum = requireNonNull(checksum, "checksum");
            return this;
        }

        /**
         * Sets the Base64-encoded SHA-256 checksum for this library.
         *
         * @param checksum Base64-encoded SHA-256 checksum
         * @return this builder
         */
        public Builder checksum(String checksum) {
            return checksum(Base64.getDecoder().decode(requireNonNull(checksum, "checksum")));
        }

        /**
         * Adds a jar relocation to apply to this library.
         *
         * @param relocation jar relocation to apply
         * @return this builder
         */
        public Builder relocate(Relocation relocation) {
            relocations.add(requireNonNull(relocation, "relocation"));
            return this;
        }

        /**
         * Adds a jar relocation to apply to this library.
         *
         * @param pattern          search pattern
         * @param relocatedPattern replacement pattern
         * @return this builder
         */
        public Builder relocate(String pattern, String relocatedPattern) {
            return relocate(new Relocation(pattern, relocatedPattern));
        }

        /**
         * Creates a new library using this builder's configuration.
         *
         * @return new library
         */
        public Library build() {
            return new Library(urls, groupId, artifactId, version, classifier, checksum, relocations);
        }
    }
}
