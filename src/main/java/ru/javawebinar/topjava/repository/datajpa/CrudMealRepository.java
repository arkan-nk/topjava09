package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

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

    @Query(name=Meal.ALL_SORTED)
    List<Meal> getAll(@Param("userId") Integer userId);

    @Query(name=Meal.GET_BETWEEN)
    List<Meal> getBetween(@Param("userId") Integer userId,
                          @Param("startDate") LocalDateTime startDate,
                          @Param("endDate") LocalDateTime endDate);


}
