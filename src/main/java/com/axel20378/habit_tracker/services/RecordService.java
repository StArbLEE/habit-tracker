package com.axel20378.habit_tracker.services;

import com.axel20378.habit_tracker.dto.HabitRequest;
import com.axel20378.habit_tracker.dto.HabitResponse;
import com.axel20378.habit_tracker.dto.RecordResponse;
import com.axel20378.habit_tracker.entity.Habit;
import com.axel20378.habit_tracker.exceptions.HabitNotFoundException;
import com.axel20378.habit_tracker.repository.HabitRepository;
import com.axel20378.habit_tracker.repository.RecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.axel20378.habit_tracker.entity.Record;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
public class RecordService {

    private final RecordRepository recordRepository; // репозиторий для записей
    private final HabitRepository habitRepository;   // репозиторий для привычек


    public RecordService(RecordRepository recordRepository, HabitRepository habitRepository) {
        this.recordRepository = recordRepository;
        this.habitRepository = habitRepository;
    }

    @Transactional
    public RecordResponse create(Long habitId) {
        // ищем привычку в БД — если не найдена бросит HabitNotFoundException
        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() -> new HabitNotFoundException(habitId));
        Record record = new Record(); // создаем новую запись
        record.setHabit(habit);       // привязываем привычку
        record.setDate(LocalDate.now()); // ставим текущую дату
        Record saved = recordRepository.save(record); // сохраняем в БД
        log.info("Отмечено выполнение привычки с id {}", saved.getId());
        return toResponse(saved); // конвертируем и возвращаем
    }

    private RecordResponse toResponse(Record record) { // конвертация Record в RecordResponse
        return new RecordResponse(
                record.getId(),
                record.getHabit().getId(), // достаем id из объекта Habit
                record.getDate()
        );
    }
}
