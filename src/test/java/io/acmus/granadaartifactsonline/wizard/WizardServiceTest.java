package io.acmus.granadaartifactsonline.wizard;

import io.acmus.granadaartifactsonline.artifact.Artifact;
import io.acmus.granadaartifactsonline.artifact.ArtifactRepository;
import io.acmus.granadaartifactsonline.system.exception.ObjectNotFoundException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WizardServiceTest {
    @Mock
    WizardRepository wizardRepository;

    @Mock
    ArtifactRepository artifactRepository;

    @InjectMocks
    WizardService wizardService;

    List<Wizard> wizards;

    @BeforeEach
    void setUp() {
        Wizard w1 = new Wizard();
        w1.setId(1);
        w1.setName("Albus Dumbledore");

        Wizard w2 = new Wizard();
        w2.setId(2);
        w2.setName("Harry Potter");

        Wizard w3 = new Wizard();
        w3.setId(3);
        w3.setName("Neville Longbottom");

        this.wizards = new ArrayList<>();
        this.wizards.add(w1);
        this.wizards.add(w2);
        this.wizards.add(w3);

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void testFindByIdSuccess() {
        // Given
        Wizard w = new Wizard();
        w.setId(1);
        w.setName("Albus Dumbledore");

        given(this.wizardRepository.findById(1)).willReturn(Optional.of(w));

        // When
        Wizard returnedWizard = this.wizardService.findById(1);

        // Then
        assertThat(returnedWizard.getId()).isEqualTo(w.getId());
        assertThat(returnedWizard.getName()).isEqualTo(w.getName());
        verify(this.wizardRepository, times(1)).findById(1);

    }

    @Test
    void testFindByIdNotFound() {

        // Given
        given(this.wizardRepository.findById(any(Integer.class))).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> {
            Wizard wizard = this.wizardService.findById(1);
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find wizard with Id 1");
        verify(this.wizardRepository, times(1)).findById(Mockito.any(Integer.class));
    }

    @Test
    void testFindAllSuccess() {
        // Given
        given(this.wizardRepository.findAll()).willReturn(this.wizards);

        // When
        List<Wizard> wizardList = this.wizardService.findAll();
        // Then
        assertThat(wizardList.size()).isEqualTo(this.wizards.size());
        verify(this.wizardRepository, times(1)).findAll();
    }

    @Test
    void testSaveSuccess() {
        // Given
        Wizard newWizard = new Wizard();
        newWizard.setName("Hermione Granger");

        given(this.wizardRepository.save(newWizard)).willReturn(newWizard);

        // When
        Wizard returnedWizard = this.wizardService.save(newWizard);

        // Then
        assertThat(returnedWizard.getName()).isEqualTo(newWizard.getName());
        verify(this.wizardRepository, times(1)).save(newWizard);

    }

    @Test
    void testUpdateSuccess() {
        // Given
        Wizard oldWizard = new Wizard();
        oldWizard.setId(1);
        oldWizard.setName("Albus Dumbledore");

        Wizard update = new Wizard();
        update.setName("Albus Dumbledore - update");

        given(this.wizardRepository.findById(1)).willReturn(Optional.of(oldWizard));
        given(this.wizardRepository.save(oldWizard)).willReturn(oldWizard);

        // When
        Wizard updatedWizard = this.wizardService.update(1, update);

        // Then
        assertThat(updatedWizard.getId()).isEqualTo(1);
        assertThat(updatedWizard.getName()).isEqualTo(update.getName());
        verify(this.wizardRepository, times(1)).findById(1);
        verify(this.wizardRepository, times(1)).save(oldWizard);
    }

    @Test
    void testUpdateNotFound() {
        // Given
        Wizard update = new Wizard();
        update.setName("Albus Dumbledore - update");

        given(this.wizardRepository.findById(1)).willReturn(Optional.empty());

        // When
        assertThrows(ObjectNotFoundException.class, () -> {
            this.wizardService.update(1, update);
        });

        // Then
        verify(this.wizardRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteSuccess() {
        // Given
        Wizard wizard = new Wizard();
        wizard.setId(1);
        wizard.setName("Albus Dumbledore");

        given(this.wizardRepository.findById(1)).willReturn(Optional.of(wizard));
        doNothing().when(this.wizardRepository).deleteById(1);

        // When
        this.wizardService.delete(1);

        // Then
        verify(this.wizardRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteNotFound() {
        // Given
        given(this.wizardRepository.findById(1)).willReturn(Optional.empty());

        // When
        assertThrows(ObjectNotFoundException.class, () -> {
            this.wizardService.delete(1);
        });

        // Then
        verify(this.wizardRepository, times(1)).findById(1);
    }

    @Test
    void testAssignArtifactSuccess() {

        // Given
        Artifact a2 = new Artifact();
        a2.setId("1250808601744904192");
        a2.setName("Invisibility Cloak");
        a2.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a2.setImageUrl("ImageUrl");

        Wizard w2 = new Wizard();
        w2.setId(2);
        w2.setName("Harry Potter");
        w2.addArtifacts(a2);

        Wizard w3 = new Wizard();
        w3.setId(3);
        w3.setName("Neville Longbottom");

        given(this.artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(a2));
        given(this.wizardRepository.findById(3)).willReturn(Optional.of(w3));

        // When
        this.wizardService.assignArtifact(3, "1250808601744904192");

        // Then
        assertThat(a2.getOwner().getId()).isEqualTo(3);
        assertThat(w3.getArtifacts().contains(a2));

    }

    @Test
    void testAssignArtifactErrorWithNonExistentWizardId() {

        // Given
        Artifact a2 = new Artifact();
        a2.setId("1250808601744904192");
        a2.setName("Invisibility Cloak");
        a2.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a2.setImageUrl("ImageUrl");

        Wizard w2 = new Wizard();
        w2.setId(2);
        w2.setName("Harry Potter");
        w2.addArtifacts(a2);

        given(this.artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(a2));
        given(this.wizardRepository.findById(3)).willReturn(Optional.empty());

        // When
        Throwable thrown = assertThrows(ObjectNotFoundException.class, () -> {
            this.wizardService.assignArtifact(3, "1250808601744904192");
        });

        // Then
        assertThat(thrown).isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find wizard with Id 3");
        assertThat(a2.getOwner().getId()).isEqualTo(2);

    }


    @Test
    void testAssignArtifactErrorWithNonExistentArtifactId() {

        // Given
        given(this.artifactRepository.findById("1250808601744904192")).willReturn(Optional.empty());

        // When
        Throwable thrown = assertThrows(ObjectNotFoundException.class, () -> {
            this.wizardService.assignArtifact(3, "1250808601744904192");
        });
        // Then
        assertThat(thrown).isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find artifact with Id 1250808601744904192");

    }


}
