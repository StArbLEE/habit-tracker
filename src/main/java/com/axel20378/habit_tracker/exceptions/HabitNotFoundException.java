package com.axel20378.habit_tracker.exceptions;

public class HabitNotFoundException extends RuntimeException{ // наследуем и создаем новый класс
    public HabitNotFoundException(Long id){
        super("Привычка с id " + id + " не найдена");
    }
}
