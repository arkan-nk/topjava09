package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    @Autowired
    private CrudMealRepository crudMealRepository;
    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            User userReference = crudUserRepository.getOne(userId);
            meal.setUser(userReference);
        }
        return meal.isNew() || get(meal.getId(), userId)!=null ? crudMealRepository.save(meal) : null;
    }

    @Override
    public boolean delete(int id, int userId) {
        boolean result = get(id, userId)!=null;
        if (result) {
           crudMealRepository.delete(id);
        }
        return result;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = crudMealRepository.findOne(id);
        return meal!=null && meal.getUser().getId()==userId ? meal : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return crudMealRepository.getAll(userId);
    }

    @Override
    public Collection<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudMealRepository.getBetween(Timestamp.valueOf(startDate), Timestamp.valueOf(endDate), userId);
    }
}
