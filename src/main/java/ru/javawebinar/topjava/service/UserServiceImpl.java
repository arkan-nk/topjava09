package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.repository.UserRepository;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
@Profile(value = {Profiles.JPA, Profiles.JDBC})
public class UserServiceImpl extends UserServiceCommon {

    @Autowired
    public void setUserRepository (UserRepository repository){
        this.repository = repository;
    }
}
