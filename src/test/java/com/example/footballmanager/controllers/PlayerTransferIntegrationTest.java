package com.example.footballmanager.controllers;

import com.example.footballmanager.dto.PlayerCreateDto;
import com.example.footballmanager.dto.PlayerDto;
import com.example.footballmanager.dto.TeamCreateDto;
import com.example.footballmanager.dto.TeamDto;
import com.example.footballmanager.util.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PlayerTransferIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String playerUrl(Long playerId, Long targetTeamId) {
        return "http://localhost:" + port + "/players/" + playerId + "/transfer/" + targetTeamId;
    }

    private Long team1Id;
    private Long team2Id;
    private Long playerId;

    @BeforeEach
    void setup() {
        TeamCreateDto team1 = TestDataFactory.createTestTeamCreateDto();
        team1.setName("Team One");
        TeamCreateDto team2 = TestDataFactory.createTestTeamCreateDto();
        team2.setName("Team Two");

        ResponseEntity<TeamDto> response1 = restTemplate.postForEntity(
                "http://localhost:" + port + "/teams", team1, TeamDto.class);
        ResponseEntity<TeamDto> response2 = restTemplate.postForEntity(
                "http://localhost:" + port + "/teams", team2, TeamDto.class);

        team1Id = response1.getBody().getId();
        team2Id = response2.getBody().getId();

        PlayerCreateDto player = TestDataFactory.createTestPlayerCreateDto(team1Id);

        ResponseEntity<PlayerDto> playerResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/players", player, PlayerDto.class);
        playerId = playerResponse.getBody().getId();
    }

    @Test
    void testPlayerTransfer() {
        ResponseEntity<String> response = restTemplate.exchange(
                playerUrl(playerId, team2Id),
                HttpMethod.PATCH,
                null,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Player transferred successfully");

        ResponseEntity<PlayerDto> playerResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/players/" + playerId, PlayerDto.class);
        assertThat(playerResponse.getBody().getTeamId()).isEqualTo(team2Id);

        ResponseEntity<TeamDto> updatedTeam1 = restTemplate.getForEntity(
                "http://localhost:" + port + "/teams/" + team1Id, TeamDto.class);
        ResponseEntity<TeamDto> updatedTeam2 = restTemplate.getForEntity(
                "http://localhost:" + port + "/teams/" + team2Id, TeamDto.class);

        BigDecimal team1BalanceAfter = updatedTeam1.getBody().getBalance();
        BigDecimal team2BalanceAfter = updatedTeam2.getBody().getBalance();

        assertThat(team1BalanceAfter).isGreaterThan(new BigDecimal("1000000"));
        assertThat(team2BalanceAfter).isLessThan(new BigDecimal("1000000"));
    }
}

