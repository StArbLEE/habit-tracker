package com.axel20378.habit_tracker.service;

import com.axel20378.habit_tracker.dto.HabitDateResponse;
import com.axel20378.habit_tracker.dto.StatsResponse;
import com.axel20378.habit_tracker.entity.Habit;
import com.axel20378.habit_tracker.entity.Record;
import com.axel20378.habit_tracker.exceptions.HabitNotFoundException;
import com.axel20378.habit_tracker.repository.HabitRepository;
import com.axel20378.habit_tracker.repository.RecordRepository;
import com.axel20378.habit_tracker.services.StatsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // подключаем Mockito
public class StatsServiceTest {
    @Mock
    private HabitRepository habitRepository;

    @Mock
    private RecordRepository recordRepository;

    @InjectMocks
    private StatsService statsService;

    @Test
    void getHabitStats(){
        Habit habit = new Habit(1L, "Спорт", "Бегать каждый день", 30, LocalDateTime.now(), null);
        Record record = new Record(1L, habit, LocalDate.now());
        when(habitRepository.findById(1L)).thenReturn(Optional.of(habit));
        when(recordRepository.findByHabitId(1L)).thenReturn(List.of(record));

        StatsResponse response = statsService.getHabitStats(1L);

        assertNotNull(response);
        assertEquals(1, response.getTotalCompletions());

    }
    @Test
    void getByIdNotFound(){
        when(habitRepository.findById(99L)).thenReturn(Optional.empty()); // мок не находит привычку

        assertThrows(HabitNotFoundException.class, () -> statsService.getHabitStats(99L));
    }
    @Test
    void getDateStats(){
        Habit habit1 = new Habit(1L, "Спорт", "Бегать", 30, LocalDateTime.now(), null);
        Habit habit2 = new Habit(2L, "Йога", "Утром", 20, LocalDateTime.now(), null);
        List<Habit> habits = List.of(habit1, habit2);

        when(habitRepository.findAll()).thenReturn(habits);
        when(recordRepository.existsByHabitIdAndDate(1L, LocalDate.now())).thenReturn(true);
        when(recordRepository.existsByHabitIdAndDate(2L, LocalDate.now())).thenReturn(false);

        HabitDateResponse response = statsService.getDateStats();

        assertNotNull(response);
        assertEquals(2, response.getHabits().size());
        assertTrue(response.getHabits().get(0).isCompleted());
        assertFalse(response.getHabits().get(1).isCompleted());
    }
}
