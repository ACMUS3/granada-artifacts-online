package io.acmus.granadaartifactsonline.artifact;

import io.acmus.granadaartifactsonline.artifact.converter.ArtifactDtoToArtifactConverter;
import io.acmus.granadaartifactsonline.artifact.converter.ArtifactToArtifactDtoConverter;
import io.acmus.granadaartifactsonline.artifact.dto.ArtifactDto;
import io.acmus.granadaartifactsonline.system.Result;
import io.acmus.granadaartifactsonline.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/artifacts")
public class ArtifactController {
    private final ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter;
    private final ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter;
    private final ArtifactService artifactService;

    public ArtifactController(ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter,
                              ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter, ArtifactService artifactService) {
        this.artifactToArtifactDtoConverter = artifactToArtifactDtoConverter;
        this.artifactDtoToArtifactConverter = artifactDtoToArtifactConverter;
        this.artifactService = artifactService;
    }

    @GetMapping("/{artifactId}")
    public Result findArtifactById(@PathVariable("artifactId") String artifactId) {
        Artifact found = this.artifactService.findById(artifactId);
        ArtifactDto artifactDto = artifactToArtifactDtoConverter.convert(found);
        return new Result(true, StatusCode.SUCCESS, "Find One SUCCESS", artifactDto);
    }

    @GetMapping("/")
    public Result findAllArtifacts() {
        List<Artifact> artifactList = this.artifactService.findAll();
        List<ArtifactDto> artifactDtoList = artifactList.stream()
                .map(this.artifactToArtifactDtoConverter::convert)
                .toList();
        return new Result(true, StatusCode.SUCCESS, "Find All SUCCESS", artifactDtoList);
    }

    @PostMapping
    public Result addArtifact(
            @Valid @RequestBody ArtifactDto artifactDto) {
        Artifact artifact = artifactDtoToArtifactConverter.convert(artifactDto);
        assert artifact != null;
        Artifact savedArtifact = this.artifactService.save(artifact);
        ArtifactDto savedArtifactDto = artifactToArtifactDtoConverter.convert(savedArtifact);
        return new Result(true, StatusCode.SUCCESS, "Add SUCCESS", savedArtifactDto);
    }

    @PutMapping("/{artifactId}")
    public Result updateArtifact(
            @PathVariable String artifactId,
            @Valid @RequestBody ArtifactDto artifactDto){

        Artifact artifact = this.artifactDtoToArtifactConverter.convert(artifactDto);
        Artifact updatedArtifact = this.artifactService.update(artifactId, artifact);
        ArtifactDto updatedArtifactDto = this.artifactToArtifactDtoConverter.convert(updatedArtifact);

        return new Result(true,StatusCode.SUCCESS,"Update SUCCESS",updatedArtifactDto);
    }

    @DeleteMapping("/{artifactId}")
    public Result deleteArtifact(@PathVariable String artifactId){

        this.artifactService.delete(artifactId);


        return new Result(true,StatusCode.SUCCESS,"Delete SUCCESS");
    }


}
