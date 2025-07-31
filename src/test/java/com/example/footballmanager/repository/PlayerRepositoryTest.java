package com.example.footballmanager.repository;

import com.example.footballmanager.entity.Player;
import com.example.footballmanager.entity.Team;
import com.example.footballmanager.repositories.PlayerRepository;
import com.example.footballmanager.repositories.TeamRepository;
import com.example.footballmanager.util.TestDataFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
public class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    private Team testTeam;
    private List<Player> testPlayers;

    @BeforeEach
    public void setUp() {
        testTeam = TestDataFactory.createTestTeam();
        testPlayers = TestDataFactory.createTestPlayers(testTeam);

        testTeam.setPlayers(testPlayers);

        teamRepository.save(testTeam);
        playerRepository.saveAll(testPlayers);
    }

    @Test
    @DisplayName("Find player by ID")
    public void findById_ShouldReturnPlayer() {
        Player savedPlayer = testPlayers.get(0);
        Optional<Player> result = playerRepository.findById(savedPlayer.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Sasha");
    }

    @Test
    @DisplayName("Save new player")
    public void save_ShouldPersistPlayer() {
        Player newPlayer = Player.builder()
                .name("New Guy")
                .age(22)
                .experienceMonths(12)
                .team(testTeam)
                .build();

        Player saved = playerRepository.save(newPlayer);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("New Guy");
    }

    @Test
    @DisplayName("Update player")
    public void update_ShouldModifyPlayer() {
        Player player = testPlayers.get(0);
        player.setExperienceMonths(99);

        playerRepository.save(player);

        Player updated = playerRepository.findById(player.getId()).orElseThrow();
        assertThat(updated.getExperienceMonths()).isEqualTo(99);
    }

    @Test
    @DisplayName("Delete player")
    public void delete_ShouldRemovePlayer() {
        Player player = testPlayers.get(0);
        playerRepository.delete(player);

        Optional<Player> deleted = playerRepository.findById(player.getId());
        assertThat(deleted).isEmpty();
    }

    @Test
    @DisplayName("Find all player for specific team")
    public void findByTeam_ShouldReturnList() {
        List<Player> result = playerRepository.findAllByTeamId(testTeam.getId());

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTeam().getName()).isEqualTo("Polissya");
    }
}
