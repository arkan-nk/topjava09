package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> listMealWithExceeded0 = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        List<UserMealWithExceed> listMealWithExceeded1 = getFilteredWithExceeded1(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
    }
    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate,Integer> mpCaloriesPerDay = mealList.stream()
                .collect(Collectors.toMap(mealItem->mealItem.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));
        return mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal->
                        new UserMealWithExceed(
                                meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                                caloriesPerDay<mpCaloriesPerDay.get(meal.getDateTime().toLocalDate())
                        )
                ).collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded1(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate,Integer> mpCaloriesPerDay = new HashMap<>();
        mealList.forEach(mealItem->{
            LocalDate key = mealItem.getDateTime().toLocalDate();
            mpCaloriesPerDay.merge(key, mealItem.getCalories(), Integer::sum);
        });
        List<UserMealWithExceed> result = new ArrayList<>();
        mealList.forEach(meal->{
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                result.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                caloriesPerDay < mpCaloriesPerDay.get(meal.getDateTime().toLocalDate())));
            }
        });
        return result;
    }
}
