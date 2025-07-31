package com.example.footballmanager.services;

import com.example.footballmanager.entity.Team;
import com.example.footballmanager.repositories.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public Team create(Team team) {
        return teamRepository.save(team);
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Team findById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + id));
    }

    @Transactional
    public Team update(Long id, Team updatedTeam) {
        Team existing = findById(id);
        existing.setName(updatedTeam.getName());
        existing.setCommission(updatedTeam.getCommission());
        existing.setBalance(updatedTeam.getBalance());
        return teamRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        teamRepository.deleteById(id);
    }
}

