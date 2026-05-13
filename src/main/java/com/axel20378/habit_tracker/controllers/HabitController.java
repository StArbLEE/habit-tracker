package com.axel20378.habit_tracker.controllers;


import com.axel20378.habit_tracker.dto.HabitRequest;
import com.axel20378.habit_tracker.dto.HabitResponse;
import com.axel20378.habit_tracker.services.HabitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // обрабатывает http запросы и возвращает данные в json
@RequestMapping("/api/habits") // чтобы не писать каждый раз /habits мы используем общий префикс для всех эндпоинтов
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) { // внедряем зависимость через конструктор
        this.habitService = habitService;
    }

    @PostMapping // POST /api/habits — создать привычку
    public ResponseEntity<HabitResponse> create(@Valid @RequestBody HabitRequest request) {
        HabitResponse created = habitService.create(request); // передаем запрос в сервис
        return ResponseEntity.status(HttpStatus.CREATED).body(created); // возвращаем 201 и созданный объект
    }

    @GetMapping("/{id}") // GET /api/habits/{id} — получить привычку по id
    public ResponseEntity<HabitResponse> getById(@PathVariable Long id) {
        HabitResponse habit = habitService.getById(id); // ищем привычку по id
        return ResponseEntity.ok(habit); // возвращаем 200 и найденный объект
    }

    @GetMapping // GET /api/habits — получить все привычки
    public ResponseEntity<List<HabitResponse>> getAll() {
        List<HabitResponse> habits = habitService.getAll(); // получаем все привычки из сервиса
        return ResponseEntity.ok(habits); // возвращаем 200 и список
    }

    @PutMapping("/{id}") // PUT /api/habits/{id} — обновить привычку по id
    public ResponseEntity<HabitResponse> update(@PathVariable Long id, @Valid @RequestBody HabitRequest request) {
        HabitResponse habitsUpdate = habitService.update(id, request); // передаем id и новые данные в сервис
        return ResponseEntity.ok(habitsUpdate); // возвращаем 200 и обновленный объект
    }

    @DeleteMapping("/{id}") // DELETE /api/habits/{id} — удалить привычку по id
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        habitService.delete(id); // удаляем привычку через сервис
        return ResponseEntity.noContent().build(); // возвращаем 204 — удалено, тела нет
    }
}
