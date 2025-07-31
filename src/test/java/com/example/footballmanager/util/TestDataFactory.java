package com.example.footballmanager.util;

import com.example.footballmanager.dto.PlayerCreateDto;
import com.example.footballmanager.dto.TeamCreateDto;
import com.example.footballmanager.dto.TeamDto;
import com.example.footballmanager.entity.Player;
import com.example.footballmanager.entity.Team;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TestDataFactory {

    public static Team createTestTeam() {
        return Team.builder()
                .name("Polissya")
                .commission(0.1)
                .balance(new BigDecimal(10_000_000))
                .build();
    }

    public static TeamCreateDto createTestTeamCreateDto() {
        TeamCreateDto teamDto = new TeamCreateDto();
        teamDto.setName("Test Team");
        teamDto.setBalance(new BigDecimal(1_000_000));
        teamDto.setCommission(0.05);

        return teamDto;
    }

    public static List<Player> createTestPlayers(Team team) {
        return List.of(
                Player.builder()
                        .name("Sasha")
                        .age(25)
                        .debutDate(LocalDate.of(2010, 3, 13))
                        .team(team)
                        .build(),
                Player.builder()
                        .name("Kolya")
                        .age(30)
                        .debutDate(LocalDate.of(2010, 3, 13))
                        .team(team)
                        .build());
    }

    public static PlayerCreateDto createTestPlayerCreateDto(long teamId) {
        PlayerCreateDto playerDto = new PlayerCreateDto();
        playerDto.setName("John Smith");
        playerDto.setAge(25);
        playerDto.setDebutDate(LocalDate.of(2021, 5, 1));
        playerDto.setTeamId(teamId);

        return playerDto;
    }
}
