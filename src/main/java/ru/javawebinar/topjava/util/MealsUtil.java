package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;

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
public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    private static MealWithExceed createWithExceed(Meal meal, User user, boolean exceeded) {
        return new MealWithExceed(meal.getId(), user, meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }

    private static Collection<Meal> filterByUser(Collection<Meal> meals, User user){
        return meals.stream().filter(meal->meal.getUserId()==user.getId().intValue()).collect(Collectors.toSet());
    }
    private static Map<LocalDate, Integer> caloriesSumMap(Collection<Meal> collectionUser, LocalDate startDate, LocalDate endDate){
        return collectionUser.stream()
            .filter(meal-> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
            .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));
    }

    public static List<MealWithExceed> getFilteredWithExceeded(Collection<Meal> meals, User user, LocalDate startDate, LocalDate endDate) {
        Collection<Meal> collectionUser = filterByUser(meals, user);
        Map<LocalDate, Integer> caloriesSumByDate = caloriesSumMap(collectionUser, startDate, endDate);
        return collectionUser.stream().filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .map(meal->createWithExceed(meal, user, caloriesSumByDate.get(meal.getDate()) > user.getCaloriesPerDay()))
                .collect(Collectors.toList());
    }

    @Deprecated
    public static List<MealWithExceed> getWithExceeded(Collection<Meal> meals, User user) {
        return getFilteredWithExceeded(meals, user, LocalTime.MIN, LocalTime.MAX);
    }
    @Deprecated
    public static List<MealWithExceed> getFilteredWithExceeded(Collection<Meal> meals, User user, LocalTime startTime, LocalTime endTime) {
        Collection<Meal> collectionUser = filterByUser(meals, user);
        Map<LocalDate, Integer> caloriesSumByDate = caloriesSumMap(collectionUser, LocalDate.MIN, LocalDate.MAX);
        return collectionUser.stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExceed(meal, user, caloriesSumByDate.get(meal.getDate()) > user.getCaloriesPerDay()))
                .collect(Collectors.toList());
    }
    @Deprecated
    public static List<MealWithExceed> getFilteredWithExceededByCycle(List<Meal> meals, User user, LocalTime startTime, LocalTime endTime) {
        Collection<Meal> collectionUser = filterByUser(meals, user);
        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        collectionUser.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        final List<MealWithExceed> mealsWithExceeded = new ArrayList<>();
        collectionUser.forEach(meal -> {
            if (DateTimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                mealsWithExceeded.add(createWithExceed(meal, user, caloriesSumByDate.get(meal.getDate()) > user.getCaloriesPerDay()));
            }
        });
        return mealsWithExceeded;
    }
    public static void main(String[] args) {
        User mockUser = new User();
        mockUser.setId(-1);
        mockUser.setCaloriesPerDay(DEFAULT_CALORIES_PER_DAY);
        List<MealWithExceed> mealsWithExceeded = getFilteredWithExceeded(MEALS, mockUser, LocalTime.of(7, 0), LocalTime.of(12, 0));
        mealsWithExceeded.forEach(System.out::println);
        System.out.println(getFilteredWithExceededByCycle(MEALS, mockUser, LocalTime.of(7, 0), LocalTime.of(12, 0)));
    }
    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(0, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(0, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(0, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(0, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(0, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(0, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    );
}