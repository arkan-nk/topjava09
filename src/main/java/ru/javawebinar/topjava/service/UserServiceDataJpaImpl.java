package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

/**
 * Created by arkan on 17.02.2017.
 */
@Service
@Profile(Profiles.DATAJPA)
public class UserServiceDataJpaImpl extends UserServiceCommon {

    @Autowired
    public void setUserRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public User get(int id){
        User usr = super.get(id);
        //usr.getMeals();
        return usr;
    }
}