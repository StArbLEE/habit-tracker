package com.axel20378.habit_tracker.services;

import com.axel20378.habit_tracker.dto.*;
import com.axel20378.habit_tracker.entity.Habit;
import com.axel20378.habit_tracker.entity.Record;
import com.axel20378.habit_tracker.exceptions.HabitNotFoundException;
import com.axel20378.habit_tracker.repository.HabitRepository;
import com.axel20378.habit_tracker.repository.RecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class StatsService { // создаем класс статистики
    private final RecordRepository recordRepository; // добавляем репозиторий для записи
    private final HabitRepository habitRepository;  // добавляем репозиторий трекера привычек

    public StatsService(RecordRepository recordRepository, HabitRepository habitRepository) { // конструктор чтобы спринг понимал как работать с репозиториями
        this.recordRepository = recordRepository;
        this.habitRepository = habitRepository;
    }

    @Transactional(readOnly = true)
    public HabitDateResponse getDateStats(){
        List<HabitDateListResponse> result_list = new ArrayList<>();
        List<Habit> habits = habitRepository.findAll();
        for (Habit hb: habits){
            if (recordRepository.existsByHabitIdAndDate(hb.getId(), LocalDate.now())){
                result_list.add(new HabitDateListResponse(hb.getId(), hb.getName(), true));
            }else{
                result_list.add(new HabitDateListResponse(hb.getId(), hb.getName(), false));
            }
        }
        return new HabitDateResponse(LocalDate.now(), result_list);
    }

    @Transactional(readOnly = true)
    public StatsResponse getHabitStats(Long habitId) { // создаем метод получения статистики привычек
        Habit habit = habitRepository.findById(habitId) //
                .orElseThrow(() -> new HabitNotFoundException(habitId));

        List<Record> records = recordRepository.findByHabitId(habitId);
        int totalCompletions = records.size();

        LocalDate startDate = habit.getCreatedAt().toLocalDate();
        LocalDate today = LocalDate.now();
        long totalDays = Math.max(1, ChronoUnit.DAYS.between(startDate, today) + 1); // +1, потому что между 1 и 2 января = 1 день, но дней прошло 2
        double completionRate = ((double) totalCompletions / totalDays) * 100;

        int currentStreak = calculateCurrentStreak(records);
        int bestStreak = calculateBestStreak(records);
        log.info("Получена статистика выполнения c id: {}", habitId);

        return new StatsResponse(currentStreak, bestStreak, completionRate, totalCompletions);
    }

    @Transactional(readOnly = true)
    public HabitWeekResponse getWeekStats(){
        List <HabitWeekListResponse> result_list = new ArrayList<>();
        List<Habit> habits = habitRepository.findAll();
        for (int i = 0; i<7; i++){
            LocalDate day = LocalDate.now().minusDays(i);
            int totalCounts = habits.size();
            int completedCount = 0;
            for (Habit hb : habits) {
                if (recordRepository.existsByHabitIdAndDate(hb.getId(), day)) {
                    completedCount++;
                }
            }
            result_list.add(new HabitWeekListResponse(day, completedCount, totalCounts));
        }return new HabitWeekResponse(result_list);
    }

    private int calculateCurrentStreak(List<Record> records) {
        if (records.isEmpty()) return 0;

        List<LocalDate> sortedDates = records.stream()
                .map(Record::getDate)
                .sorted(Comparator.reverseOrder())
                .toList();

        int streak = 0;
        LocalDate expectedDate = LocalDate.now();

        for (LocalDate date : sortedDates) {
            if (date.equals(expectedDate)) {
                streak++;
                expectedDate = expectedDate.minusDays(1);
            } else {
                break; // Прерываем при первом же несовпадении
            }
        }
        return streak;
    }
    private int calculateBestStreak(List<Record> records) {
        if (records.isEmpty()) return 0;

        List<LocalDate> sortedDates = records.stream()
                .map(Record::getDate)
                .sorted()
                .toList();

        int bestStreak = 1;
        int currentStreak = 1;

        for (int i = 1; i < sortedDates.size(); i++) {
            LocalDate prev = sortedDates.get(i - 1);
            LocalDate curr = sortedDates.get(i);

            if (prev.plusDays(1).equals(curr)) {
                currentStreak++;
            } else {
                currentStreak = 1;
            }
            bestStreak = Math.max(bestStreak, currentStreak);
        }
        return bestStreak;
    }
}
