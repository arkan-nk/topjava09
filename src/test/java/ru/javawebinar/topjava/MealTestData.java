package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.STARTID;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final Meal ETALON_MEAL1_USER = new Meal(STARTID.START_SEQ.getVal()+2, LocalDateTime.of(2015, 5, 1, 9,0,37), "Завтрак", 100);
    public static final Meal ETALON_MEAL2_USER = new Meal(STARTID.START_SEQ.getVal()+3, LocalDateTime.of(2015, 5, 1, 19,37,04), "Ужин", 100);
    public static final Meal ETALON_MEAL3_USER = new Meal(STARTID.START_SEQ.getVal()+4, LocalDateTime.of(2015, 5, 2, 9,12,45), "Завтрак", 500);
    public static final Meal ETALON_MEAL4_USER = new Meal(STARTID.START_SEQ.getVal()+5, LocalDateTime.of(2015, 5, 2, 14,37,04), "Обед", 2000);
    public static final Meal ETALON_MEAL5_USER = new Meal(STARTID.START_SEQ.getVal()+6, LocalDateTime.of(2015, 5, 3, 9,0,37), "Завтрак", 1500);
    public static final Meal ETALON_MEAL6_USER = new Meal(STARTID.START_SEQ.getVal()+7, LocalDateTime.of(2015, 5, 3, 15,8,32), "Обед", 1000);

    public static final Meal ETALON_MEAL1_ADMIN = new Meal(STARTID.START_SEQ.getVal()+8, LocalDateTime.of(2015, 5, 1, 7,14,22), "Завтрак", 100);
    public static final Meal ETALON_MEAL2_ADMIN = new Meal(STARTID.START_SEQ.getVal()+9, LocalDateTime.of(2015, 5, 1, 18,34,18), "Ужин", 100);
    public static final Meal ETALON_MEAL3_ADMIN = new Meal(STARTID.START_SEQ.getVal()+10, LocalDateTime.of(2015, 5, 2, 8,24,02), "Завтрак", 1000);
    public static final Meal ETALON_MEAL4_ADMIN = new Meal(STARTID.START_SEQ.getVal()+11, LocalDateTime.of(2015, 5, 2, 13,27,22), "Обед", 1500);
    public static final Meal ETALON_MEAL5_ADMIN = new Meal(STARTID.START_SEQ.getVal()+12, LocalDateTime.of(2015, 5, 3, 10,4,0), "Завтрак", 1700);
    public static final Meal ETALON_MEAL6_ADMIN = new Meal(STARTID.START_SEQ.getVal()+13, LocalDateTime.of(2015, 5, 3, 16,14,04), "Обед", 900);

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<Meal>(
        (expected,actual)->expected==actual || (
                Objects.equals(expected.getId(), actual.getId())
                && Objects.equals(expected.getDateTime(), actual.getDateTime())
                && Objects.equals(expected.getDescription(), actual.getDescription())
                && Objects.equals(expected.getCalories(), actual.getCalories())
        )
    );
}
