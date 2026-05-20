package com.axel20378.habit_tracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "records")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // первичный ключ

    @ManyToOne(fetch = FetchType.LAZY) // много записей принадлежат одной привычке
    @JoinColumn(name = "habit_id") // колонка в таблице records которая хранит id привычки
    private Habit habit; // ссылка на объект Habit вместо просто Long habitId

    private LocalDate date; // дата выполнения привычки
}