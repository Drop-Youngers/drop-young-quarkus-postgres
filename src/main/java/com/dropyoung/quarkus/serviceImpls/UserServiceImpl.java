package com.dropyoung.quarkus.serviceImpls;

import com.dropyoung.quarkus.models.User;
import com.dropyoung.quarkus.repositories.UserRepository;
import com.dropyoung.quarkus.services.IUserService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public void update(User user) {
        this.userRepository.persist(user);
    }

    @Override
    public User findById(UUID id) {
        return this.userRepository.findById(id);
    }

    @Override
    public List<User> getAll() {
        return this.userRepository.listAll();
    }

    @Override
    public void delete(UUID id) {
        this.userRepository.deleteById(id);
    }
}
