package ru.javawebinar.topjava.services;

import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.repository.RepositoryMealIf;
import ru.javawebinar.topjava.util.NotFoundException;

import java.util.Map;

/**
 * Created by arkan on 13.12.2016.
 */
public interface ServicesMealIf<T extends RepositoryMealIf> {
    MealWithExceed get(Integer key) throws NotFoundException;
    MealWithExceed removeAndGetOld(Integer key) throws NotFoundException;;
    MealWithExceed putAndGetNew(MealWithExceed meal) throws NotFoundException;;
    Map<Integer, MealWithExceed> getMealsWithExceed();
    void setRepositoryMeal(T repository);
    T getRepositoryMeal();
}
