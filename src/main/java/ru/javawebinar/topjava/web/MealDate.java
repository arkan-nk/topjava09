package ru.javawebinar.topjava.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by arkan on 20.12.2016.
 */
public class MealDate {
    public LocalDateTime getStartDateTime() {
        LocalDate startDate1 = startDate!=null ? startDate : LocalDate.MIN;
        LocalTime startTime1 = startTime!=null ? startTime : LocalTime.MIN;
        return LocalDateTime.of(startDate1.getYear(),
            startDate1.getMonthValue(), startDate1.getDayOfMonth(),
            startTime1.getHour(), startTime1.getMinute());
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndDateTime() {
        LocalDate endDate1 = endDate!=null ? endDate : LocalDate.MAX;
        LocalTime endTime1 = endTime!=null ? endTime : LocalTime.MAX;
        return LocalDateTime.of(endDate1.getYear(),
                endDate1.getMonthValue(), endDate1.getDayOfMonth(),
                endTime1.getHour(), endTime1.getMinute());
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
}
