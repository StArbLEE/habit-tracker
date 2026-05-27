package com.axel20378.habit_tracker.repository;

import com.axel20378.habit_tracker.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HabitRepository extends JpaRepository<Habit, Long> {
}
