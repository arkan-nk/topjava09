package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.MealsUtil;

import static ru.javawebinar.topjava.util.STARTID.USER_ID;

/**
 * GKislin
 * 06.03.2015.
 */
public class AuthorizedUser {
    public static int id = USER_ID.getVal();

    public static int id() {
        return id;
    }

    public static void setId(int id) {
        AuthorizedUser.id = id;
    }

    public static int getCaloriesPerDay() {
        return MealsUtil.DEFAULT_CALORIES_PER_DAY;
    }
}
