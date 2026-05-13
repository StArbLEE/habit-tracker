package com.axel20378.habit_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class HabitResponse {
    private Long id;
    private String name;
    private String description;
    private int target;
    private LocalDate createdAt;
}