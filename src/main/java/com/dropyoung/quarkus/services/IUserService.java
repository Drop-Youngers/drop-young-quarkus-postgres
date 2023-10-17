package com.dropyoung.quarkus.services;

import com.dropyoung.quarkus.models.User;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    User create(User user);

    void update(User user);

    User findById(UUID id);

    User uploadProfile(UUID id, MultipartFormDataInput input);

    List<User> findAll();

    void delete(UUID id);

}
