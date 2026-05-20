package com.axel20378.habit_tracker.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class HabitRequest {
    @NotBlank(message = "Требуется имя")
    private String name;

    private String description;

    @Positive(message = "значение должно быть положительным числом.")
    private int target;
}
