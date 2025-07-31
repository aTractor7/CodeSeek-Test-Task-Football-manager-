package com.example.footballmanager.services;

import com.example.footballmanager.entity.Player;

import com.example.footballmanager.entity.Team;
import com.example.footballmanager.repositories.PlayerRepository;
import com.example.footballmanager.util.TransferCalculator;
import com.example.footballmanager.util.validation.TransferValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
        return playerRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        playerRepository.deleteById(id);
    }

    @Transactional
    public void transferPlayer(Long playerId, Long targetTeamId) {
        Player player = findById(playerId);
        Team fromTeam = player.getTeam();
        Team toTeam = teamService.findById(targetTeamId);

        TransferValidator.validateTeamsAreDifferent(fromTeam, toTeam);
        TransferValidator.validatePlayerAge(player);

        int monthsOfExperience = TransferCalculator.calculateExperienceMonths(player.getDebutDate());
        BigDecimal basePrice = TransferCalculator.calculateBaseTransferPrice(monthsOfExperience, player.getAge());
        BigDecimal commission = TransferCalculator.calculateCommission(basePrice, toTeam.getCommission());
        BigDecimal totalPrice = basePrice.add(commission);

        TransferValidator.validateBalance(toTeam, totalPrice);

        performTransfer(player, fromTeam, toTeam, totalPrice);
    }

    private void performTransfer(Player player, Team fromTeam, Team toTeam, BigDecimal totalPrice) {
        toTeam.setBalance(toTeam.getBalance().subtract(totalPrice));
        fromTeam.setBalance(fromTeam.getBalance().add(totalPrice));
        player.setTeam(toTeam);

        teamService.update(toTeam.getId(), toTeam);
        teamService.update(fromTeam.getId(), fromTeam);
        playerRepository.save(player);
    }
}

