package com.example.footballmanager.repository;

import com.example.footballmanager.entity.Team;
import com.example.footballmanager.repositories.PlayerRepository;
import com.example.footballmanager.repositories.TeamRepository;
import com.example.footballmanager.util.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    private Team testTeam;

    @BeforeEach
    public void init() {
        testTeam = TestDataFactory.createTestTeam();

        teamRepository.save(testTeam);
    }

    @Test
    @DisplayName("Find team by ID")
    public void findById_ShouldReturnTeam() {
        Optional<Team> result = teamRepository.findById(testTeam.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Polissya");
    }

    @Test
    @DisplayName("Save new team")
    public void save_ShouldPersistTeam() {
        Team newTeam = Team.builder()
                .name("Gamma FC")
                .commission(0.08)
                .balance(BigDecimal.valueOf(3_000_000))
                .build();

        Team saved = teamRepository.save(newTeam);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Gamma FC");
    }

    @Test
    @DisplayName("Update team")
    public void update_ShouldModifyTeam() {
        testTeam.setCommission(0.07);
        teamRepository.save(testTeam);

        Team updated = teamRepository.findById(testTeam.getId()).orElseThrow();
        assertThat(updated.getCommission()).isEqualTo(0.07);
    }

    @Test
    @DisplayName("Delete team")
    public void delete_ShouldRemoveTeam() {
        teamRepository.delete(testTeam);

        Optional<Team> deleted = teamRepository.findById(testTeam.getId());
        assertThat(deleted).isEmpty();
    }

    @Test
    @DisplayName("Find all team")
    public void findAll_ShouldReturnAllTeams() {
        List<Team> result = teamRepository.findAll();

        assertThat(result).hasSize(1);
        assertThat(result)
                .extracting(Team::getName)
                .containsExactlyInAnyOrder("Polissya");
    }
}

