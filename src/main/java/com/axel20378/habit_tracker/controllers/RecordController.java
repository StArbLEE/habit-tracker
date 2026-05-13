package com.axel20378.habit_tracker.controllers;


import com.axel20378.habit_tracker.dto.RecordResponse;
import com.axel20378.habit_tracker.services.RecordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // обрабатывает http запросы и возвращает данные в json
@RequestMapping("/api/habits") // общий префикс для всех эндпоинтов
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService recordService) { // внедряем зависимость через конструктор
        this.recordService = recordService;
    }

    @PostMapping("/{habitId}/records") // POST /api/habits/{habitId}/records — создать запись для привычки
    public ResponseEntity<RecordResponse> create(@PathVariable Long habitId) { // habitId берется из URL
        RecordResponse created = recordService.create(habitId); // передаем habitId в сервис
        return ResponseEntity.status(HttpStatus.CREATED).body(created); // возвращаем 201 и созданный объект
    }
}
