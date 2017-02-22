package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
public class DataJpaMealRepositoryImpl implements MealRepository {

    @Autowired
    private CrudMealRepository crudMealRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;


    @Override
    @Modifying
    @Transactional(isolation = Isolation.READ_COMMITTED)
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
    @Modifying
    @Transactional(isolation = Isolation.READ_COMMITTED)
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
