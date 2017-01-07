package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import static ru.javawebinar.topjava.util.STARTID.ADMIN_ID;
import static ru.javawebinar.topjava.util.STARTID.USER_ID;

import java.util.Objects;


/**
 * GKislin
 * 24.09.2015.
 */
public class UserTestData {
    public static final User USER_ETALON = new User(USER_ID.getVal(), "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN_ETALON = new User(ADMIN_ID.getVal(), "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);
    public static final User USER_TEST = new User(null, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final ModelMatcher<User> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getPassword(), actual.getPassword())
                            && Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getEmail(), actual.getEmail())
                            && Objects.equals(expected.getCaloriesPerDay(), actual.getCaloriesPerDay())
                            && Objects.equals(expected.isEnabled(), actual.isEnabled())
//                            && Objects.equals(expected.getRoles(), actual.getRoles())
                    )
    );
}