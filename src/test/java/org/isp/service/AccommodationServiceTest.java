package org.isp.service;

import org.isp.event.AccommodationEvent;
import org.isp.exception.ResourceNotFoundException;
import org.isp.model.Accommodation;
import org.isp.repository.AccommodationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccommodationServiceTest {

    @Mock
    private AccommodationRepository accommodationRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private AccommodationService accommodationService;

    @BeforeEach
    void setUp() {
        accommodationService = new AccommodationService(accommodationRepository, eventPublisher);
    }

    @Test
    void getAllAccommodations_ShouldReturnAllAccommodations() {
        // Arrange
        List<Accommodation> expectedAccommodations = Arrays.asList(
            createAccommodation(1L, "Test Accommodation 1"),
            createAccommodation(2L, "Test Accommodation 2")
        );
        when(accommodationRepository.findAll()).thenReturn(expectedAccommodations);

        // Act
        List<Accommodation> actualAccommodations = accommodationService.getAllAccommodations();

        // Assert
        assertThat(actualAccommodations).hasSize(2);
        assertThat(actualAccommodations).isEqualTo(expectedAccommodations);
        verify(accommodationRepository).findAll();
    }

    @Test
    void getAccommodationById_WhenExists_ShouldReturnAccommodation() {
        // Arrange
        Long id = 1L;
        Accommodation expected = createAccommodation(id, "Test Accommodation");
        when(accommodationRepository.findById(id)).thenReturn(Optional.of(expected));

        // Act
        Accommodation actual = accommodationService.getAccommodationById(id);

        // Assert
        assertThat(actual).isEqualTo(expected);
        verify(accommodationRepository).findById(id);
    }

    @Test
    void getAccommodationById_WhenNotExists_ShouldThrowException() {
        // Arrange
        Long id = 1L;
        when(accommodationRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> accommodationService.getAccommodationById(id));
        verify(accommodationRepository).findById(id);
    }

    @Test
    void saveAccommodation_ShouldSaveAndPublishEvent() {
        // Arrange
        Accommodation accommodation = createAccommodation(1L, "New Accommodation");
        when(accommodationRepository.save(any(Accommodation.class))).thenReturn(accommodation);

        // Act
        Accommodation saved = accommodationService.saveAccommodation(accommodation);

        // Assert
        assertThat(saved).isEqualTo(accommodation);
        verify(accommodationRepository).save(accommodation);
        verify(eventPublisher).publishEvent(any(AccommodationEvent.class));
    }

    @Test
    void deleteAccommodation_WhenExists_ShouldDeleteAndPublishEvent() {
        // Arrange
        Long id = 1L;
        Accommodation accommodation = createAccommodation(id, "Test Accommodation");
        when(accommodationRepository.findById(id)).thenReturn(Optional.of(accommodation));

        // Act
        accommodationService.deleteAccommodation(id);

        // Assert
        verify(accommodationRepository).deleteById(id);
        verify(eventPublisher).publishEvent(any(AccommodationEvent.class));
    }

    private Accommodation createAccommodation(Long id, String name) {
        Accommodation accommodation = new Accommodation();
        accommodation.setId(id);
        accommodation.setName(name);
        accommodation.setAddress("Test Address");
        return accommodation;
    }
}
