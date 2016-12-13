package ru.javawebinar.topjava.services;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.repository.RepositoryMealIf;
import ru.javawebinar.topjava.util.NotFoundException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arkadiy on 14.12.2016.
 */
public class ServicesMeal<T extends RepositoryMealIf> implements ServicesMealIf<T>{
    private T repositoryMeal;
    private Map<LocalDate, Integer> caloriesPerDay = new HashMap<>();
    @Override
    public MealWithExceed get(Integer key) throws NotFoundException {
        Meal meal = repositoryMeal.get(key);

        return null;
    }

    @Override
    public MealWithExceed removeAndGetOld(Integer key) throws NotFoundException {
        Meal meal = repositoryMeal.removeAndGetOld(key);
        caloriesPerDay.remove(meal.getDate());
        return null;
    }

    @Override
    public MealWithExceed putAndGetNew(MealWithExceed meal) throws NotFoundException {
        return null;
    }

    @Override
    public Map<Integer, MealWithExceed> getMealsWithExceed() {
        return null;
    }

    @Override
    public void setRepositoryMeal(T repository) {
        this.repositoryMeal = repository;
    }

    @Override
    public T getRepositoryMeal() {
        return null;
    }
}
