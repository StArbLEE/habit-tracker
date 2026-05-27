package com.axel20378.habit_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class HabitDateResponse {
    private LocalDate date;
    private List<HabitDailyListResponse> habits;
}
