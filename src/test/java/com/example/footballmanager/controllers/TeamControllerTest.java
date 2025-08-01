package com.example.footballmanager.controllers;

import com.example.footballmanager.dto.TeamCreateDto;
import com.example.footballmanager.dto.TeamDto;
import com.example.footballmanager.repositories.TeamRepository;
import com.example.footballmanager.util.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TeamControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TeamRepository teamRepository;

    private String getUrl(String path) {
        return "http://localhost:" + port + "/teams" + path;
    }

    @BeforeEach
    void setUp() {
        teamRepository.deleteAll();
    }

    @Test
    @DisplayName("Create and Get one Team")
    void testCreateAndGetTeam() {
        TeamCreateDto team = TestDataFactory.createTestTeamCreateDto();

        ResponseEntity<TeamDto> createResponse = restTemplate.postForEntity(getUrl(""), team, TeamDto.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        TeamDto createdTeam = createResponse.getBody();
        assertThat(createdTeam).isNotNull();
        assertThat(createdTeam.getName()).isEqualTo("Test Team");

        ResponseEntity<TeamDto> getResponse = restTemplate.getForEntity(getUrl("/" + createdTeam.getId()), TeamDto.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getId()).isEqualTo(createdTeam.getId());
    }

    @Test
    @DisplayName("Get all Teams")
    void testGetAllTeams() {
        ResponseEntity<TeamDto[]> response = restTemplate.getForEntity(getUrl(""), TeamDto[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
