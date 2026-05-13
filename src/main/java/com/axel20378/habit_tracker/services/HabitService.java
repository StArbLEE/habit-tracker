package com.axel20378.habit_tracker.services;

import com.axel20378.habit_tracker.dto.HabitRequest;
import com.axel20378.habit_tracker.dto.HabitResponse;
import com.axel20378.habit_tracker.exceptions.HabitNotFoundException;
import com.axel20378.habit_tracker.models.Habit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class HabitService {
    private Map<Long, Habit> habits = new HashMap<>();
    private Long nextId = 1L;

    public List<HabitResponse> getAll() {
        log.info("Получение всех привычек, количество: {}", habits.size());
        List<HabitResponse> result = new ArrayList<>();
        for (Habit habit : habits.values()) {
            result.add(toResponse(habit));
        }
        return result;
    }

    public HabitResponse getById(Long id) { // создаем функцию для получения по id
        try {
            Habit habit = habits.get(id); // ищем привычку по ключу в мапе
            if (habit == null) {
                log.warn("Привычка с id {} не найдена", id); // предупреждение если не найдена
                throw new HabitNotFoundException(id);
            }
            log.info("Получена привычка с id: {}", id);
            return toResponse(habit); // конвертируем и возвращаем
        } catch (HabitNotFoundException e) {
            throw e; // пробрасываем исключение дальше чтобы GlobalExceptionHandler поймал
        }
    }

    public HabitResponse create(HabitRequest request) {
        Long id = nextId++; // каждый раз увеличиваем id на одну цифру чтобы он не повторялся
        Habit habit = new Habit(id, request.getName(), request.getDescription(), request.getTarget(), LocalDate.now()); // создаем объект
        habits.put(id, habit); // кладем в мап
        log.info("Создана привычка с id: {} и именем: {}", id, request.getName());
        return toResponse(habit); // конвертируем и возвращаем
    }

    public HabitResponse update(Long id, HabitRequest request) {
        try {
            Habit habit = habits.get(id); // ищем привычку по id
            if (habit == null) {
                log.warn("Попытка обновить несуществующую привычку с id: {}", id);
                throw new HabitNotFoundException(id);
            }
            habit.setName(request.getName()); // устанавливаем новое имя
            habit.setDescription(request.getDescription()); // устанавливаем новое описание
            habit.setTarget(request.getTarget()); // устанавливаем новую цель
            log.info("Обновлена привычка с id: {}", id);
            return toResponse(habit); // конвертируем и возвращаем
        } catch (HabitNotFoundException e) {
            throw e; // пробрасываем исключение дальше чтобы GlobalExceptionHandler поймал
        }
    }

    public void delete(Long id) {
        getById(id); // проверяем существует ли привычка, иначе выбрасываем исключение
        habits.remove(id); // удаляем из мапа
        log.info("Удалена привычка с id: {}", id);
    }

    private HabitResponse toResponse(Habit habit) { // метод конвертации Habit в HabitResponse
        return new HabitResponse(
                habit.getId(),
                habit.getName(),
                habit.getDescription(),
                habit.getTarget(),
                habit.getCreatedAt()
        );
    }
}