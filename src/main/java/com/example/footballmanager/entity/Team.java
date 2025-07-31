package com.example.footballmanager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "teams")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team implements AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // Commission percentage: 0.0 to 0.1
    @Column(nullable = false)
    private Double commission;

    @Column(nullable = false)
    private BigDecimal balance;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players;

    public void addPlayer(Player player) {
        if (players == null)
            players = new ArrayList<>();

        player.setTeam(this);
        players.add(player);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Team team)) return false;
        return Objects.equals(id, team.id) && Objects.equals(name, team.name) && Objects.equals(commission, team.commission) && Objects.equals(balance, team.balance) && Objects.equals(players, team.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, commission, balance, players);
    }
}

