package com.dropyoung.quarkus.serviceImpls;

import com.dropyoung.quarkus.exceptions.CustomBadRequestException;
import com.dropyoung.quarkus.models.File;
import com.dropyoung.quarkus.models.User;
import com.dropyoung.quarkus.repositories.UserRepository;
import com.dropyoung.quarkus.services.IFileService;
import com.dropyoung.quarkus.services.IUserService;
import com.dropyoung.quarkus.services.MailService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.util.List;
import java.util.UUID;


@ApplicationScoped
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final MailService mailService;
    private final IFileService fileService;

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
    public User uploadProfile(UUID id, MultipartFormDataInput input) {
        User user = this.userRepository.findById(id);
        File file = this.fileService.save(user, input);
        user.setProfileImage(file);
        this.userRepository.persist(user);
        return user;
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
