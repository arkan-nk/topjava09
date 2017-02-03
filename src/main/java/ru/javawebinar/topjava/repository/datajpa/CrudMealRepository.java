package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.sql.Timestamp;
import java.util.Collection;

/**
 * gkislin
 * 02.10.2016
 */
@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Override
    Meal findOne(Integer id);


    @Transactional
    @Override
    @Modifying
    void delete(Integer id);

    @Transactional
    @Override
    @Modifying
    Meal save(Meal meal);


    @Query(Meal.ALL_SORTED)
    Collection<Meal> getAll(@Param("userId") int userId);


    @Query(Meal.GET_BETWEEN)
    Collection<Meal> getBetween(@Param("startDate") Timestamp startDate,
                                        @Param("endDate") Timestamp endDate,
                                        @Param("userId") int userId);
}
