package com.example.footballmanager.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

public class TransferCalculator {

    public static int calculateExperienceMonths(LocalDate debutDate) {
        Period period = Period.between(debutDate, LocalDate.now());
        int months = period.getYears() * 12 + period.getMonths();
        if (months <= 0) {
            throw new IllegalArgumentException("Player must have at least 1 month of experience.");
        }
        return months;
    }

    public static BigDecimal calculateBaseTransferPrice(int monthsExperience, int age) {
        if (age <= 0) {
            throw new IllegalArgumentException("Player age must be greater than 0.");
        }

        return BigDecimal.valueOf(monthsExperience)
                .multiply(BigDecimal.valueOf(100_000))
                .divide(BigDecimal.valueOf(age), 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateCommission(BigDecimal basePrice, Double commissionRate) {
        return basePrice.multiply(BigDecimal.valueOf(commissionRate));
    }
}
