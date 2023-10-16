package com.dropyoung.quarkus.serviceImpls;

import com.dropyoung.quarkus.exceptions.CustomBadRequestException;
import com.dropyoung.quarkus.models.User;
import com.dropyoung.quarkus.repositories.UserRepository;
import com.dropyoung.quarkus.services.IUserService;
import com.dropyoung.quarkus.services.MailService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;


@ApplicationScoped
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final MailService mailService;

    @Override
    public User create(User user) {
        try {
            this.userRepository.persist(user);
            this.mailService.sendWelcomeEmail(user.getEmail(), user.getNames());
            return user;
        } catch (Exception e) {
            System.out.println(e.getCause().toString());
            throw new CustomBadRequestException(e.getMessage());
        }
    }

    @Override
    public void update(User user) {
        try {
            this.userRepository.persist(user);
        } catch (Exception e) {
            throw new CustomBadRequestException(e.getMessage());
        }
    }

    @Override
    public User findById(UUID id) {
        return this.userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.listAll();
    }

    @Override
    public void delete(UUID id) {
        this.userRepository.deleteById(id);
    }
}
