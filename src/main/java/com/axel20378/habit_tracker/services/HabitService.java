package com.axel20378.habit_tracker.services;
import com.axel20378.habit_tracker.repository.*;
import com.axel20378.habit_tracker.dto.HabitRequest;
import com.axel20378.habit_tracker.dto.HabitResponse;
import com.axel20378.habit_tracker.exceptions.HabitNotFoundException;
import com.axel20378.habit_tracker.entity.Habit;
import com.axel20378.habit_tracker.repository.HabitRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Slf4j
@Service
public class HabitService {
    private final HabitRepository habitRepository;

    public HabitService(HabitRepository habitRepository){
        this.habitRepository = habitRepository;
    }

    @Transactional(readOnly = true)
    public List<HabitResponse> getAll() {
        List<HabitResponse> result = new ArrayList<>();
        result = habitRepository.findAll().stream().map(this::toResponse).toList();
        log.info("Получение всех привычек, количество: {}", result.size());
        return result;
    }
    @Transactional(readOnly = true)
    public HabitResponse getById(Long id) { // создаем функцию для получения по id
        try {
            Habit habit = habitRepository.findById(id).orElseThrow(() -> new HabitNotFoundException(id)); // ищем привычку по ключу в мапе
            log.info("Получена привычка с id: {}", id);
            return toResponse(habit); // конвертируем и возвращаем
        } catch (HabitNotFoundException e) {
            log.warn("Привычка с id {} не найдена", id);
            throw e; // пробрасываем исключение дальше чтобы GlobalExceptionHandler поймал
        }
    }
    @Transactional
    public HabitResponse create(HabitRequest request) {
        Habit habit = new Habit();
        habit.setName(request.getName());
        habit.setDescription(request.getDescription());
        habit.setTarget(request.getTarget());
        habit.setCreatedAt(LocalDateTime.now());
        Habit saved = habitRepository.save(habit);
        log.info("Создана привычка с id: {} и именем: {}", saved.getId(), saved.getName());
        return toResponse(saved); // конвертируем и возвращаем
    }

    @Transactional
    public HabitResponse update(Long id, HabitRequest request) {
        try {
            Habit habit = habitRepository.findById(id)
                    .orElseThrow(() -> new HabitNotFoundException(id));
            habit.setName(request.getName()); // устанавливаем новое имя
            habit.setDescription(request.getDescription()); // устанавливаем новое описание
            habit.setTarget(request.getTarget()); // устанавливаем новую цель
            Habit saved = habitRepository.save(habit);
            log.info("Обновлена привычка с id: {}", id);
            return toResponse(saved); // конвертируем и возвращаем
        } catch (HabitNotFoundException e) {
            log.warn("Попытка обновить несуществующую привычку с id: {}", id);
            throw e; // пробрасываем исключение дальше чтобы GlobalExceptionHandler поймал
        }
    }

    @Transactional
    public void delete(Long id) {
        getById(id); // проверяем существует ли привычка, иначе выбрасываем исключение
        habitRepository.deleteById(id); // удаляем из мапа
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