package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class Meal extends BaseEntity {
    private LocalDateTime dateTime;

    private String description;

    private int calories;

    public Meal(){
        this(null, null, 0);
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public void setDateTime(LocalDateTime dt){
        dateTime = dt;
    }
    public void setDescription(String de){
        description = de;
    }
    public void setCalories(int cal){
        calories = cal;
    }

    public LocalDate getDate() {
        return dateTime!=null?dateTime.toLocalDate():null;
    }

    public LocalTime getTime() {
        return dateTime!=null?dateTime.toLocalTime():null;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
