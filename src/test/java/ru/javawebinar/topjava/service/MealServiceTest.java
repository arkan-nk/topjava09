package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.STARTID;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import static ru.javawebinar.topjava.MealTestData.MATCHER;

/**
 * Created by Arkadiy on 06.01.2017.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    private DbPopulator dbPopulator;
    @Autowired
    private MealService service;

    @Before
    public void setUp(){
        dbPopulator.execute();
    }

    @Test
    public void getOwn() throws NotFoundException{
        Assert.assertFalse("userId is not matched", STARTID.USER_ID.getVal()!=AuthorizedUser.id);
        Meal actualMeal = service.get(MealTestData.ETALON_MEAL1_USER.getId(), AuthorizedUser.id);
        MATCHER.assertEquals(MealTestData.ETALON_MEAL1_USER, actualMeal);
    }

    @Test
    public void deleteOwn() throws NotFoundException {
        Assert.assertFalse("userId is not matched", STARTID.USER_ID.getVal()!=AuthorizedUser.id);
        service.delete(MealTestData.ETALON_MEAL1_USER.getId(), AuthorizedUser.id);
        Collection expectedCollection = Arrays.asList(MealTestData.ETALON_MEAL6_USER,
                MealTestData.ETALON_MEAL5_USER, MealTestData.ETALON_MEAL4_USER,
                MealTestData.ETALON_MEAL3_USER, MealTestData.ETALON_MEAL2_USER);
        Collection<Meal> actualCollection = service.getAll(AuthorizedUser.id);
        MATCHER.assertCollectionEquals(expectedCollection, actualCollection);
    }

    @Test(expected = NotFoundException.class)
    public void getAlien() throws Exception {
        Assert.assertFalse("userId is matched", STARTID.USER_ID.getVal()!=AuthorizedUser.id);
        service.get(MealTestData.ETALON_MEAL1_USER.getId(), STARTID.ADMIN_ID.getVal());
    }

    @Test(expected = NotFoundException.class)
    public void deleteAlien() throws Exception {
        Assert.assertFalse("userId is matched", STARTID.USER_ID.getVal()!=AuthorizedUser.id);
        service.delete(MealTestData.ETALON_MEAL1_USER.getId(), STARTID.ADMIN_ID.getVal());
    }

    @Test
    public void getBetweenDatesOwn() throws Exception {
        Assert.assertFalse("userId is not matched", STARTID.USER_ID.getVal()!=AuthorizedUser.id);
        Collection<Meal> actualCollection = service.getBetweenDates(LocalDate.of(2015, 5,2), LocalDate.of(2015,5,3), AuthorizedUser.id);
        Collection<Meal> expectedCollection = Arrays.asList(
                MealTestData.ETALON_MEAL6_USER, MealTestData.ETALON_MEAL5_USER,
                MealTestData.ETALON_MEAL4_USER, MealTestData.ETALON_MEAL3_USER);
        MATCHER.assertCollectionEquals(expectedCollection, actualCollection);
    }

    @Test
    public void getBetweenDateTimesOwn() throws Exception {
        Assert.assertFalse("userId is not matched", STARTID.USER_ID.getVal()!=AuthorizedUser.id);
        Collection<Meal> actualMeals = service.getBetweenDateTimes(LocalDateTime.of(2015, 5,2,0,0,0), LocalDateTime.of(2015,5,3, 9,59,59), AuthorizedUser.id);
        Collection<Meal> expectedCollection = Arrays.asList(
                MealTestData.ETALON_MEAL5_USER, MealTestData.ETALON_MEAL4_USER, MealTestData.ETALON_MEAL3_USER);
        MATCHER.assertCollectionEquals(expectedCollection, actualMeals);
    }

    @Test
    public void getAllOwn() {
        Assert.assertFalse(STARTID.USER_ID.getVal()!=AuthorizedUser.id);
        Collection<Meal> actualMeals = service.getAll(AuthorizedUser.id);
        Collection<Meal> expectedMeals =  Arrays.asList(MealTestData.ETALON_MEAL6_USER,
                MealTestData.ETALON_MEAL5_USER, MealTestData.ETALON_MEAL4_USER,
                MealTestData.ETALON_MEAL3_USER, MealTestData.ETALON_MEAL2_USER,
                MealTestData.ETALON_MEAL1_USER);
        MATCHER.assertCollectionEquals(expectedMeals, actualMeals);
    }

    @Test
    public void updateOwn() throws NotFoundException {
        Assert.assertFalse("userId is not match",STARTID.USER_ID.getVal()!=AuthorizedUser.id);
        Meal changedMeal = new Meal(MealTestData.ETALON_MEAL1_USER.getId(),
                MealTestData.ETALON_MEAL1_USER.getDateTime(),
                MealTestData.ETALON_MEAL1_USER.getDescription(),800);
        service.update(changedMeal, AuthorizedUser.id);
        Meal expectedMeal = service.get(changedMeal.getId(), AuthorizedUser.id);
        MATCHER.assertEquals(expectedMeal, changedMeal);
    }

    @Test(expected = NotFoundException.class)
    public void updateAlien() throws NotFoundException {
        Assert.assertFalse("userId is match",STARTID.USER_ID.getVal()!=AuthorizedUser.id);
        service.update(MealTestData.ETALON_MEAL1_USER, STARTID.ADMIN_ID.getVal());
    }

    @Test
    public void saveOwn() {
        Assert.assertFalse("userId is not matched",STARTID.USER_ID.getVal()!=AuthorizedUser.id);
        Meal saving = new Meal(LocalDateTime.of(2015, 5,3, 18,20,55), "Ужин", 460);
        Meal actualMeal = service.save(saving, AuthorizedUser.id);
        Meal expectedMeal = new Meal(STARTID.START_SEQ.getVal() + 14, LocalDateTime.of(2015, 5,3, 18,20,55), "Ужин", 460);
        MATCHER.assertEquals(expectedMeal, actualMeal);
    }
}