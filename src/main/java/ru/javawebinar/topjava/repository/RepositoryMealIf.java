package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Map;

/**
 * Created by arkan on 13.12.2016.
 */
public interface RepositoryMealIf {
    Meal get(Integer key);
    Meal removeAndGetOld(Integer key);
    Meal putAndGetNew(Meal meal);
    Map<Integer, Meal> getMeals();
}
