package io.acmus.granadaartifactsonline.artifact;

import io.acmus.granadaartifactsonline.artifact.utils.IdWorker;
import io.acmus.granadaartifactsonline.wizard.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtifactServiceTest {
    @Mock
    ArtifactRepository artifactRepository;
    @Mock
    IdWorker idWorker;
    @InjectMocks
    ArtifactService artifactService;
    List<Artifact> artifacts;

    @BeforeEach
    void setUp() {
        artifacts = new ArrayList<>();
        Artifact a1 = new Artifact();
        a1.setId("1250808601744904191");
        a1.setName("Deluminator");
        a1.setDescription("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.");
        a1.setImageUrl("ImageUrl");

        Artifact a2 = new Artifact();
        a2.setId("1250808601744904192");
        a2.setName("Invisibility Cloak");
        a2.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a2.setImageUrl("ImageUrl");

        Artifact a3 = new Artifact();
        a3.setId("1250808601744904193");
        a3.setName("Elder Wand");
        a3.setDescription("The Elder Wand, known throughout history as the Deathstick or the Wand of Destiny, is an extremely powerful wand made of elder wood with a core of Thestral tail hair.");
        a3.setImageUrl("ImageUrl");

        artifacts.add(a1);
        artifacts.add(a2);
        artifacts.add(a3);

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void testFindByIdSuccess() {
        // Given = prepare fake data.
        // arrange inputs and targets.
        // define the behavior of mock object ArtifactRepository.
        Artifact a = new Artifact();
        a.setId("458151525154");
        a.setName("Invisibility Clock");
        a.setId("An Invisibility Clock is used to make the wearer invisible");
        a.setImageUrl("ImageUrl");
        Wizard w = new Wizard();
        w.setId(2);
        w.setName("Mario Tabby");
        a.setOwner(w);

        given(artifactRepository.findById("458151525154")).willReturn(Optional.of(a));

        // when = call the method to be tested.
        // act on the target behavior.
        // when steps should cover the method to be tested.
        Artifact returnedArtifact = artifactService.findById("458151525154");
        // then = assert step.
        // expected outcomes.
        assertThat(returnedArtifact.getId()).isEqualTo(a.getId());
        assertThat(returnedArtifact.getName()).isEqualTo(a.getName());

        verify(artifactRepository, times(1)).findById("458151525154");


    }

    @Test
    void testFindByIdNotFound() {
        // Given
        given(artifactRepository.findById(Mockito.any(String.class)))
                .willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> {
            Artifact returnedArtifact = artifactService.findById("458151525154");
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(ArtifactNotFoundException.class)
                .hasMessage("Couldn't find artifact with id 458151525154");

        verify(artifactRepository, times(1)).findById("458151525154");
    }

    @Test
    void testFindAllSuccess() {

        // Given
        given(artifactRepository.findAll()).willReturn(this.artifacts);

        // When
        List<Artifact> artifactList = artifactService.findAll();

        // Then
        assertThat(artifactList.size()).isEqualTo(artifacts.size());
        verify(artifactRepository, times(1)).findAll();
    }

    @Test
    void testSaveSuccess() {
        // Given
        Artifact newArtifact = new Artifact();
        newArtifact.setName("Artifact 3");
        newArtifact.setDescription("Description");
        newArtifact.setImageUrl("ImageUrl..");

        given(idWorker.nextId()).willReturn(123456L);
        given(artifactRepository.save(newArtifact)).willReturn(newArtifact);

        // When
        Artifact savedArtifact = artifactService.save(newArtifact);
        // Then
        assertThat(savedArtifact.getId()).isEqualTo("123456");
        assertThat(savedArtifact.getName()).isEqualTo(newArtifact.getName());
        assertThat(savedArtifact.getDescription()).isEqualTo(newArtifact.getDescription());
        assertThat(savedArtifact.getImageUrl()).isEqualTo(newArtifact.getImageUrl());

        verify(artifactRepository, times(1)).save(newArtifact);

    }

    @Test
    void testUpdateSuccess() {
        // Given
        Artifact oldArtifact = new Artifact();

        oldArtifact.setId("1250808601744904192");
        oldArtifact.setName("Invisibility Clock");
        oldArtifact.setDescription("An Invisibility Clock is used to make the wearer invisible");
        oldArtifact.setImageUrl("ImageUrl");

        Artifact update = new Artifact();

        update.setId("1250808601744904192");
        update.setName("Invisibility Clock");
        update.setDescription("A new description.");
        update.setImageUrl("ImageUrl");

        given(this.artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(oldArtifact));

        given(this.artifactRepository.save(oldArtifact)).willReturn(oldArtifact);

        // When
        Artifact updatedArtifact = this.artifactService.update("1250808601744904192", update);

        // Then
        assertThat(updatedArtifact.getId()).isEqualTo(update.getId());
        assertThat(updatedArtifact.getDescription()).isEqualTo(update.getDescription());

        verify(artifactRepository, times(1)).findById("1250808601744904192");
        verify(artifactRepository, times(1)).save(oldArtifact);

    }

    @Test
    void testUpdateNotFound() {
        // Given

        Artifact update = new Artifact();

        update.setName("Invisibility Clock");
        update.setId("A new description.");
        update.setImageUrl("ImageUrl");


        given(this.artifactRepository.findById("1250808601744904192")).willReturn(Optional.empty());

        // When
        assertThrows(ArtifactNotFoundException.class, () -> {
            this.artifactService.update("1250808601744904192", update);
        });

        // Then
        verify(artifactRepository, times(1)).findById("1250808601744904192");

    }

    @Test
    void testDeleteSuccess() {
        // Given
        Artifact oldArtifact = new Artifact();

        oldArtifact.setId("1250808601744904192");
        oldArtifact.setName("Invisibility Clock");
        oldArtifact.setDescription("An Invisibility Clock is used to make the wearer invisible");
        oldArtifact.setImageUrl("ImageUrl");

        given(artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(oldArtifact));

        doNothing().when(artifactRepository).deleteById("1250808601744904192");

        // When
        this.artifactService.delete("1250808601744904192");

        // Then
        verify(artifactRepository, times(1)).deleteById("1250808601744904192");

    }

    @Test
    void testDeleteNotFound() {

        // Given
        given(artifactRepository.findById("1250808601744904192")).willReturn(Optional.empty());

        // When
        assertThrows(ArtifactNotFoundException.class,()->{
            this.artifactService.delete("1250808601744904192");
        });
        // Then
        verify(artifactRepository, times(1)).findById("1250808601744904192");

    }


}

