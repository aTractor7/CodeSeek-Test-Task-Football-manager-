package com.example.footballmanager.controllers;

import com.example.footballmanager.dto.PlayerCreateDto;
import com.example.footballmanager.dto.PlayerDto;
import com.example.footballmanager.dto.TeamCreateDto;
import com.example.footballmanager.dto.TeamDto;
import com.example.footballmanager.util.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class PlayerControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Long teamId;

    private String getUrl(String path) {
        return "http://localhost:" + port + "/players" + path;
    }

    private String getTeamUrl(String path) {
        return "http://localhost:" + port + "/teams" + path;
    }

    @BeforeEach
    void createTeamIfNotExists() {
        TeamCreateDto team = TestDataFactory.createTestTeamCreateDto();
        ResponseEntity<TeamDto> response = restTemplate.postForEntity(getTeamUrl(""), team, TeamDto.class);
        teamId = response.getBody().getId();
    }

    @Test
    @DisplayName("Create and Get one Player")
    void testCreateAndGetPlayer() {
        PlayerCreateDto player = TestDataFactory.createTestPlayerCreateDto(teamId);

        ResponseEntity<PlayerDto> createResponse = restTemplate.postForEntity(getUrl(""), player, PlayerDto.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        PlayerDto createdPlayer = createResponse.getBody();
        assertThat(createdPlayer).isNotNull();
        assertThat(createdPlayer.getName()).isEqualTo("John Smith");

        ResponseEntity<PlayerDto> getResponse = restTemplate.getForEntity(getUrl("/" + createdPlayer.getId()), PlayerDto.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getId()).isEqualTo(createdPlayer.getId());
    }
}