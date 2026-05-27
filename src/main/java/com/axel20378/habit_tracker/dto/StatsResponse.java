package com.axel20378.habit_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class StatsResponse {
    private int currentStreak;
    private int bestStreak;
    private double completionRate;
    private int totalCompletions;
}
