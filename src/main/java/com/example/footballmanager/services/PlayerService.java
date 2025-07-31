package com.example.footballmanager.services;

import com.example.footballmanager.entity.Player;

import com.example.footballmanager.entity.Team;
import com.example.footballmanager.repositories.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamService teamService;

    @Transactional
    public Player create(Player player) {
        Team team = teamService.findById(player.getTeam().getId());
        player.setTeam(team);
        team.addPlayer(player);

        return playerRepository.save(player);
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Player findById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Player not found with id: " + id));
    }

    @Transactional
    public Player update(Long id, Player updatedPlayer) {
        Player existing = findById(id);
        existing.setName(updatedPlayer.getName());
        existing.setAge(updatedPlayer.getAge());
        existing.setDebutDate(updatedPlayer.getDebutDate());
        existing.setTeam(updatedPlayer.getTeam());
        return playerRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        playerRepository.deleteById(id);
    }
}

