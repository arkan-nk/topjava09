package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {
    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Meal save(Meal meal, int userId) {
        int updateResult = -1;
        if (meal.isNew()){
            SimpleJdbcInsert insertMeal = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("meals").usingGeneratedKeyColumns("id");
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                    .addValue("id", meal.getId())
                    .addValue("date_time", meal.getDateTime())
                    .addValue("user_id", userId)
                    .addValue("description", meal.getDescription())
                    .addValue("calories", meal.getCalories());
            Number key = insertMeal.executeAndReturnKey(mapSqlParameterSource);
            meal.setId(key.intValue());
            updateResult=1;
        }else {
            updateResult = jdbcTemplate.update("UPDATE meals SET date_time=?, description=?, calories=? WHERE id=? AND user_id=?",
                    saveStmt -> {
                        saveStmt.setTimestamp(1, Timestamp.valueOf(meal.getDateTime()));
                        saveStmt.setString(2, meal.getDescription());
                        saveStmt.setInt(3, meal.getCalories());
                        saveStmt.setInt(4, meal.getId());
                        saveStmt.setInt(5, userId);
                    });
        }
        return updateResult>0? meal : null;
    }

    @Override
    public boolean delete(int id, int userId) {
        int result = jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", delStmt -> {
            delStmt.setInt(1, id);
            delStmt.setInt(2, userId);
        });
        return result!=0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = getResultMeals(id, userId, DateTimeUtil.MIN_DATE_TIME, DateTimeUtil.MAX_DATE_TIME);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getResultMeals(null, userId, DateTimeUtil.MIN_DATE_TIME, DateTimeUtil.MAX_DATE_TIME);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return getResultMeals(null, userId, startDate, endDate);
    }
    private List<Meal> getResultMeals(Integer id, Integer userId, LocalDateTime startDate, LocalDateTime endDate){
        return jdbcTemplate.query(
                "SELECT id, date_time, description, calories FROM meals " +
                        "WHERE id=COALESCE(?, id) AND user_id=COALESCE(?, user_id) " +
                          "AND date_time BETWEEN ? AND ? ORDER BY user_id, date_time DESC",
                selectStmt->{
                     if(id!=null) {
                         selectStmt.setInt(1, id);
                     } else {
                         selectStmt.setNull(1, Types.INTEGER);
                     }
                     if (userId!=null) {
                         selectStmt.setInt(2, userId);
                     } else {
                         selectStmt.setNull(2, Types.INTEGER);
                     }
                     selectStmt.setTimestamp(3, Timestamp.valueOf(startDate));
                     selectStmt.setTimestamp(4, Timestamp.valueOf(endDate));
                },
                ROW_MAPPER
        );
    }
}
