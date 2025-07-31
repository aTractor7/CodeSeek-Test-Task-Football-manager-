package com.example.footballmanager.util.validation;

import com.example.footballmanager.entity.Player;
import com.example.footballmanager.entity.Team;

import java.math.BigDecimal;

public class TransferValidator {

    public static void validateTeamsAreDifferent(Team fromTeam, Team toTeam) {
        if (fromTeam.getId().equals(toTeam.getId())) {
            throw new IllegalArgumentException("Player already belongs to this team.");
        }
    }

    public static void validateBalance(Team buyer, BigDecimal totalPrice) {
        if (buyer.getBalance().compareTo(totalPrice) < 0) {
            throw new IllegalArgumentException("Target team doesn't have enough balance.");
        }
    }

    public static void validatePlayerAge(Player player) {
        if (player.getAge() <= 0) {
            throw new IllegalArgumentException("Player age must be greater than 0.");
        }
    }
}
