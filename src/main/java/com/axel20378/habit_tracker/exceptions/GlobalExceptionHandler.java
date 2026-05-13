package com.axel20378.habit_tracker.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice  // следит за всеми контроллерами если вылетает исключение то Spring смотрит сюда
public class GlobalExceptionHandler{
    @ExceptionHandler(HabitNotFoundException.class) // говорит что именно этот метод будет ловить HabitNotFoundException, если другое вылетит то этот метод не сработает
    public ResponseEntity<String> handleHabitNotFound(HabitNotFoundException ex){ // поймманное исключение передается сюда как параметр, чтобы можно было достать его сообщение
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}