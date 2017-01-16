package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User user = new User();
        user.setId(userId);
        meal.setUser(user);
        if (meal.isNew()){
            em.persist(meal);
            return meal;
        }
        Meal m = this.get(meal.getId(), userId);
        if (m!=null) {
            em.merge(meal);
        }
        return m;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        Meal m = this.get(id, userId);
        if (m!=null) {
            em.remove(m);
        }
        return m!=null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal m = em.find(Meal.class, id);
        return m.getUser().getId().equals(userId) ? m : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return this.getBetween(LocalDateTime.of(DateTimeUtil.MIN_DATE, LocalTime.MIN),
                    LocalDateTime.of(DateTimeUtil.MAX_DATE, LocalTime.MAX), userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        Query query = em.createNativeQuery("SELECT id, date_time, description, calories, user_id FROM meals WHERE user_id=? AND date_time BETWEEN ? AND ? ORDER BY date_time DESC", Meal.class);
        query.setParameter(1, userId);
        query.setParameter(2, startDate);
        query.setParameter(3, endDate);
        return query.getResultList();
    }
}