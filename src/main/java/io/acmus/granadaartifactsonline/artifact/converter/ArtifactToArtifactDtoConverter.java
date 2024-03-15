package io.acmus.granadaartifactsonline.artifact.converter;

import io.acmus.granadaartifactsonline.artifact.Artifact;
import io.acmus.granadaartifactsonline.artifact.ArtifactDto;
import io.acmus.granadaartifactsonline.wizard.WizardToWizardDtoConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArtifactToArtifactDtoConverter implements Converter<Artifact, ArtifactDto> {
    private final WizardToWizardDtoConverter wizardToWizardDtoConverter;

    public ArtifactToArtifactDtoConverter(WizardToWizardDtoConverter wizardToWizardDtoConverter) {
        this.wizardToWizardDtoConverter = wizardToWizardDtoConverter;
    }

    @Override
    public ArtifactDto convert(Artifact source) {
        ArtifactDto artifactDto = new ArtifactDto(
                source.getId(),
                source.getName(),
                source.getDescription(),
                source.getImageUrl(),
                source.getOwner() != null
                        ? this.wizardToWizardDtoConverter.convert(source.getOwner()) : null
        );
        return artifactDto;
    }
}