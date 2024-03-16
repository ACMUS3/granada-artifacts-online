package io.acmus.granadaartifactsonline.artifact;

public class ArtifactNotFoundException extends RuntimeException {
    public ArtifactNotFoundException(String message) {
        super("Couldn't find artifact with id " + message);
    }

    public ArtifactNotFoundException() {
        super("Couldn't find any artifact");
    }
}
