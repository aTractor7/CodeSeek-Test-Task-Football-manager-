package com.example.footballmanager.entity;

import jakarta.persistence.*;
import jdk.jfr.Timespan;
import jdk.jfr.Timestamp;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player implements AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "debut_date", nullable = false)
    private LocalDate debutDate;

    @Column(nullable = false)
    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Player player)) return false;
        return Objects.equals(id, player.id) && Objects.equals(name, player.name) && Objects.equals(debutDate, player.debutDate) && Objects.equals(age, player.age) && Objects.equals(team, player.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, debutDate, age, team);
    }
}
