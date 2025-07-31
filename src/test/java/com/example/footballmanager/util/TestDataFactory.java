package com.example.footballmanager.util;

import com.example.footballmanager.entity.Player;
import com.example.footballmanager.entity.Team;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

public class TestDataFactory {

    public static Team createTestTeam() {
        return Team.builder()
                .name("Polissya")
                .commission(0.1)
                .balance(new BigDecimal(10_000_000))
                .build();
    }

    public static List<Player> createTestPlayers(Team team) {
        return List.of(
                Player.builder()
                        .name("Sasha")
                        .age(25)
                        .experienceMonths(20)
                        .team(team)
                        .build(),
                Player.builder()
                        .name("Kolya")
                        .age(30)
                        .experienceMonths(40)
                        .team(team)
                        .build());
    }
}
