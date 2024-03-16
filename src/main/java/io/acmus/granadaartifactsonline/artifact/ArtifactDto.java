package io.acmus.granadaartifactsonline.artifact;

import io.acmus.granadaartifactsonline.wizard.WizardDto;
import jakarta.validation.constraints.NotEmpty;

public record ArtifactDto(

        String id,
        @NotEmpty(message = "Name is required")
        String name,
        @NotEmpty(message = "Description is required")
        String description,
        @NotEmpty(message = "ImageUrl is required")
        String imageUrl,
        WizardDto owner) {
}
