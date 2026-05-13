package com.axel20378.habit_tracker.services;

import com.axel20378.habit_tracker.dto.RecordResponse;
import org.springframework.stereotype.Service;
import com.axel20378.habit_tracker.models.Record;
import com.axel20378.habit_tracker.models.Habit;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecordService {
    private Long nextId = 1L;
    private final Map<Long, Record> records = new HashMap<>();
    private final HabitService habitService;
    public RecordService(HabitService habitService){this.habitService = habitService;};

    public RecordResponse create(Long habitId) {
        habitService.getById(habitId); // проверяем что привычка существует
        Record record = new Record(nextId++, habitId, LocalDate.now()); // создаем запись
        records.put(record.getId(), record); // кладем в мап
        return toResponse(record); // конвертируем и возвращаем
    }

    private RecordResponse toResponse(Record record) {
        return new RecordResponse(
                record.getId(),
                record.getHabitId(),
                record.getDate()
        );
    }
}
