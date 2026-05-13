package com.axel20378.habit_tracker.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@AllArgsConstructor
// аннотация которая создает автоматически для наших полей сеттеры и геттеры и тп
public class Habit {
    private Long id;// айди нашего трекера
    private String name;
    private String description;
    private int target;
    private LocalDate createdAt;
}