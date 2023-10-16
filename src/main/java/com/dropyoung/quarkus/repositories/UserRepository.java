package com.dropyoung.quarkus.repositories;

import com.dropyoung.quarkus.models.User;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.UUID;

public class UserRepository implements PanacheRepositoryBase<User, UUID> {

    public User findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public User findByVerificationCode(String verificationCode) {
        return find("verificationCode", verificationCode).firstResult();
    }

    public User findByPasswordResetCode(String passwordResetCode) {
        return find("passwordResetCode", passwordResetCode).firstResult();
    }


}
