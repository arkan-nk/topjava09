package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        Meal result = null;
        if (meal.isNew() || get(meal.getId(), userId)!=null) {
            User userReference = crudUserRepository.getOne(userId);
            meal.setUser(userReference);
            result = crudMealRepository.save(meal);
        }
        return result;
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
        User eater = meal!=null ? meal.getUser() : null;
        return eater!=null && eater.getId()==userId ? meal : null;
    }


    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> result = crudMealRepository.getAll(userId);
        return result!=null && !result.isEmpty() ? result : Collections.emptyList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        List<Meal> result = crudMealRepository.getBetween(userId, startDate, endDate);
        return result!=null && !result.isEmpty() ? result : Collections.emptyList();
    }
}
