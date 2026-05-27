package com.axel20378.habit_tracker.repository;


import com.axel20378.habit_tracker.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findByHabitId(Long habitId);
    boolean existsByHabitIdAndDate(Long habitId, LocalDate date);
}
