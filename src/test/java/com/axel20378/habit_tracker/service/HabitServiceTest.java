package com.axel20378.habit_tracker.service;

import com.axel20378.habit_tracker.dto.HabitRequest;
import com.axel20378.habit_tracker.dto.HabitResponse;
import com.axel20378.habit_tracker.entity.Habit;
import com.axel20378.habit_tracker.exceptions.HabitNotFoundException;
import com.axel20378.habit_tracker.repository.HabitRepository;
import com.axel20378.habit_tracker.services.HabitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // подключаем Mockito
class HabitServiceTest {
    @Mock
    private HabitRepository habitRepository;

    @InjectMocks
    private HabitService habitService;

    @Test
    void createHabit() {
        // Arrange — подготавливаем данные
        HabitRequest request = new HabitRequest("Спорт", "Бегать каждый день", 30);
        Habit savedHabit = new Habit(1L, "Спорт", "Бегать каждый день", 30, LocalDateTime.now(), null);
        when(habitRepository.save(any(Habit.class))).thenReturn(savedHabit);

        // Act — вызываем метод сервиса
        HabitResponse response = habitService.create(request);

        assertNotNull(response);
        assertEquals("Спорт", response.getName());
        assertEquals(30, response.getTarget());

    }
    @Test
    void getByIdExisting(){
        // Arrange
        Habit habit = new Habit(1L, "Спорт", "Бегать каждый день", 30, LocalDateTime.now(), null);
        when(habitRepository.findById(1L)).thenReturn(Optional.of(habit)); // мок находит привычку

        // Act
        HabitResponse response = habitService.getById(1L);

        //Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Спорт", response.getName());
    }
    @Test
    void getByIdNotFound(){
        when(habitRepository.findById(99L)).thenReturn(Optional.empty()); // мок не находит привычку

        assertThrows(HabitNotFoundException.class, () -> habitService.getById(99L));
    }
    @Test
    void updateHabit(){
        Habit habit = new Habit(1L, "Спорт", "Бегать каждый день", 30, LocalDateTime.now(), null);
        HabitRequest request = new HabitRequest("Йога", "Каждое утро", 20);
        when(habitRepository.findById(1L)).thenReturn(Optional.of(habit));
        when(habitRepository.save(any(Habit.class))).thenReturn(habit);

        HabitResponse response = habitService.update(1L, request);

        assertEquals("Йога", response.getName());
        assertEquals(20, response.getTarget());

    }
    @Test
    void deleteHabit(){
        Habit habit = new Habit(1L, "Спорт", "Бегать каждый день", 30, LocalDateTime.now(), null);
        when(habitRepository.findById(1L)).thenReturn(Optional.of(habit));

        habitService.delete(1L);

        // проверяем что deleteById был вызван именно с id=1
        verify(habitRepository).deleteById(1L);
    }
}
