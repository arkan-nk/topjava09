package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.MealsUtil;

/**
 * GKislin
 * 06.03.2015.
 */
public class AuthorizedUser {

    private static int id;
    private static int calories = MealsUtil.DEFAULT_CALORIES_PER_DAY;

    public static void setId(int id1){
        id = id1;
    }

    public static void setCalories(int calories1){
        calories = calories1;
    }

    public static int id() {
        return id;
    }

    public static int getCaloriesPerDay() {
        return calories;
    }
}
