package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal save(Meal meal){
        if (meal.isNew())
            LOG.info("save new meal");
        else LOG.info("save meal id="+meal.getId());
        return service.save(meal);
    }

    public void delete(int id) throws NotFoundException{
        LOG.info("delete meal id="+id);
        service.delete(id);
    }

    public Meal get(int id) throws NotFoundException{
        LOG.info("get meal id="+id);
        return service.get(id);
    }
    public List<MealWithExceed> getAll(User user, LocalDateTime startDate, LocalDateTime endDate){
        LOG.info("getAll from period [" + DateTimeUtil.toString(startDate) + " - " +  DateTimeUtil.toString(endDate) + "] for user id=" + user.getId());
        List<MealWithExceed> mealWithExceeds = service.getAll(user, startDate.toLocalDate(), endDate.toLocalDate());
        return mealWithExceeds.size()<1 ? Collections.emptyList() : mealWithExceeds.stream()
            .filter(meal->
                DateTimeUtil.isBetween(
                        meal.getDateTime().toLocalTime(),
                        startDate.toLocalTime(), endDate.toLocalTime())
            ).sorted(Comparator.comparing(MealWithExceed::getDateTime)).collect(Collectors.toList());
    }
}
