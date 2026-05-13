package com.axel20378.habit_tracker.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data // аннотация которая создает автоматически для наших полей сеттеры и геттеры и тп
public class Record {
    private Long id;
    private Long habitId;
    private LocalDate date;
}
