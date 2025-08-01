package com.example.footballmanager.repositories;

import com.example.footballmanager.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findAllByTeamId(Long teamId);
}
