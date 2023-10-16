package com.dropyoung.quarkus.services;

import com.dropyoung.quarkus.models.User;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    User create(User user);
    void update(User user);
    User findById(UUID id);

    List<User> findAll();
    void delete(UUID id);

}
