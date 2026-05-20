package com.axel20378.habit_tracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor // создает пустой конструктор — нужен для JPA и Jackson
@Data // создает геттеры, сеттеры, toString, equals, hashCode
@AllArgsConstructor // создает конструктор со всеми полями
@Entity // говорит JPA что этот класс — таблица в базе данных
@Table(name = "habits") // явно указываем имя таблицы в БД
public class Habit {

    @Id // это поле — первичный ключ таблицы
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id генерируется автоматически базой данных (AUTO INCREMENT)
    private Long id;

    private String name;        // колонка name в таблице
    private String description; // колонка description в таблице
    private int target;         // колонка target в таблице
    private LocalDateTime createdAt; // колонка created_at в таблице

    @OneToMany(
            mappedBy = "habit",          // поле в классе Record которое ссылается на Habit
            cascade = CascadeType.ALL,   // все операции (сохранение, удаление) применяются и к записям
            orphanRemoval = true,        // если запись удалена из списка — удалить её и из БД
            fetch = FetchType.LAZY       // записи загружаются из БД только когда они реально нужны
    )
    private List<Record> records = new ArrayList<>(); // список всех записей связанных с этой привычкой
}