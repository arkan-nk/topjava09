package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by arkan on 13.12.2016.
 */
public class InMemoryRepositoryMeal implements RepositoryMealIf{
    private ConcurrentMap<Integer, Meal> store = new ConcurrentHashMap<>();
    private AtomicInteger sequenceId=new AtomicInteger(0);
    @Override
    public Meal get(Integer key) {
        return store.getOrDefault(key, null);
    }

    @Override
    public Meal removeAndGetOld(Integer key) {
        return store.remove(key);
    }

    @Override
    public Meal putAndGetNew(Meal meal) {
        if (meal.getId()==null) {
            final int generatedID = sequenceId.getAndIncrement();
            Meal meal1 = new Meal(generatedID, meal.getDateTime(), meal.getDescription(), meal.getCalories());
            store.put(generatedID, meal1);
            return meal1;
        }
        store.replace(meal.getId(), meal);
        return meal;
    }

    @Override
    public Map<Integer, Meal> getMeals() {
        Map<Integer, Meal> snapshot = new HashMap<>();
        if (!store.isEmpty()) snapshot.putAll(store);
        return snapshot;
    }
}