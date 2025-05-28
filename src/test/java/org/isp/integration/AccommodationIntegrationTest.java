package org.isp.integration;

import org.isp.model.Accommodation;
import org.isp.repository.AccommodationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AccommodationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllAccommodations_ShouldReturnAllAccommodations() throws Exception {
        // Arrange
        Accommodation accommodation = new Accommodation();
        accommodation.setName("Test Accommodation");
        accommodation.setAddress("Test Address");
        accommodationRepository.save(accommodation);

        // Act & Assert
        mockMvc.perform(get("/api/v1/accommodations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].name", is("Test Accommodation")))
                .andExpect(jsonPath("$[0].address", is("Test Address")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createAccommodation_ShouldCreateNewAccommodation() throws Exception {
        // Arrange
        Accommodation accommodation = new Accommodation();
        accommodation.setName("New Accommodation");
        accommodation.setAddress("New Address");

        // Act & Assert
        mockMvc.perform(post("/api/v1/accommodations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accommodation)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("New Accommodation")))
                .andExpect(jsonPath("$.address", is("New Address")));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAccommodationById_WhenExists_ShouldReturnAccommodation() throws Exception {
        // Arrange
        Accommodation accommodation = new Accommodation();
        accommodation.setName("Test Accommodation");
        accommodation.setAddress("Test Address");
        Accommodation saved = accommodationRepository.save(accommodation);

        // Act & Assert
        mockMvc.perform(get("/api/v1/accommodations/{id}", saved.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Test Accommodation")))
                .andExpect(jsonPath("$.address", is("Test Address")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteAccommodation_WhenExists_ShouldDeleteAccommodation() throws Exception {
        // Arrange
        Accommodation accommodation = new Accommodation();
        accommodation.setName("Test Accommodation");
        accommodation.setAddress("Test Address");
        Accommodation saved = accommodationRepository.save(accommodation);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/accommodations/{id}", saved.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getAccommodations_WithoutAuth_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/accommodations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
