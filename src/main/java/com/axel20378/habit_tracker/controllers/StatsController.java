package com.axel20378.habit_tracker.controllers;

import com.axel20378.habit_tracker.dto.HabitDateResponse;
import com.axel20378.habit_tracker.dto.StatsResponse;
import com.axel20378.habit_tracker.services.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StatsController {
    private final StatsService statsService;

    public StatsController(StatsService statsService){
        this.statsService = statsService;
    }
    @GetMapping("habits/{id}/stats")
    public ResponseEntity<StatsResponse> getHabitStats(@PathVariable Long id){
        StatsResponse stats = statsService.getHabitStats(id);
        return ResponseEntity.ok(stats);
    }
    @GetMapping("stats/daily")
    public ResponseEntity<HabitDateResponse> getDailyStats(){
        HabitDateResponse stats = statsService.getDailyStats();
        return ResponseEntity.ok(stats);
    }

}
