package com.axel20378.habit_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitDailyListResponse {
    private Long id;
    private String name;
    private boolean completed;
}
