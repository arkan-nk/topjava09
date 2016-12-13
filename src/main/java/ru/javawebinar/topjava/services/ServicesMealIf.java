package ru.javawebinar.topjava.services;

import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.repository.RepositoryMealIf;

import java.util.Map;

/**
 * Created by arkan on 13.12.2016.
 */
public interface ServicesMealIf {
    MealWithExceed get(Integer key);
    MealWithExceed removeAndGetOld(Integer key);
    MealWithExceed putAndGetNew(MealWithExceed meal);
    Map<Integer, MealWithExceed> getMealsWithExceed();
    void setRepositoryMeal(RepositoryMealIf repository);
    RepositoryMealIf getReposotory();
}
