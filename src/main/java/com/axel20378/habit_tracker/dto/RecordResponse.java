package com.axel20378.habit_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class RecordResponse{
    private Long id;
    private Long habitId;
    private LocalDate date;
}
