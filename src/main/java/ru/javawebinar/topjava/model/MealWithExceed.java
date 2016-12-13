package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class MealWithExceed {
    private final int id;

    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    public MealWithExceed(int idd, LocalDateTime dateTime, String description, int calories, boolean exceed) {
        id = idd;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }
    public int getId(){
        return id;
    }
    public LocalDate getDate(){
        return dateTime.toLocalDate();
    }
    public LocalTime getTime(){
        return dateTime.toLocalTime();
    }
    public String getDescription(){
        return description;
    }
    public Integer getCalories(){
        return Integer.valueOf(calories);
    }
    public Boolean getExceed(){
        return exceed;
    }

    @Override
    public String toString() {
        return "UserMealWithExceed{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }
}
