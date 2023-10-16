package com.dropyoung.quarkus.serviceImpls;

import com.dropyoung.quarkus.enums.EPasswordResetStatus;
import com.dropyoung.quarkus.enums.EVerificationStatus;
import com.dropyoung.quarkus.exceptions.CustomBadRequestException;
import com.dropyoung.quarkus.models.User;
import com.dropyoung.quarkus.payload.AuthResponse;
import com.dropyoung.quarkus.repositories.UserRepository;
import com.dropyoung.quarkus.services.IAuthService;
import com.dropyoung.quarkus.services.MailService;
import com.dropyoung.quarkus.utils.PasswordEncoder;
import com.dropyoung.quarkus.utils.TokenUtils;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Random;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @ConfigProperty(name = "com.management.jwt.duration")
    public Long duration;
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    public String issuer;

    @Override
    public AuthResponse login(String email, String password) {
        User user = this.userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(passwordEncoder.encode(password))) {
            return new AuthResponse(TokenUtils.generateToken(user.getId().toString(), user.getRoles(), duration, issuer)
                    , user);
        } else {
            throw new CustomBadRequestException("Invalid credentials");
        }
    }

    @Override
    public String initiatePasswordReset(String email) {
        User user = this.userRepository.findByEmail(email);
        if (user == null) throw new CustomBadRequestException("Invalid email");
        String passwordResetCode = String.format("%04d", new Random().nextInt(10000));
        user.setPasswordResetCode(passwordResetCode);
        user.setPasswordResetStatus(EPasswordResetStatus.PENDING);
        this.userRepository.persist(user);
        mailService.sendInitiateResetPasswordMail(email, user.getNames(), passwordResetCode);
        return "Password reset code sent to email";
    }

    @Override
    public String resetPassword(String token, String password) {
        User user = this.userRepository.findByPasswordResetCode(token);
        if (user == null) throw new CustomBadRequestException("Invalid token");
        user.setPassword(passwordEncoder.encode(password));
        user.setPasswordResetStatus(EPasswordResetStatus.NOT_REQUESTED);
        user.setPasswordResetCode(null);
        this.userRepository.persist(user);
        this.mailService.sendEmailVerifiedSuccessfullyMail(user.getEmail(), user.getNames());
        return "Password reset successfully";
    }

    @Override
    public String initiateEmailVerification(UUID id) {
        User user = this.userRepository.findById(id);
        if (user == null) throw new CustomBadRequestException("Invalid user");
        String verificationCode = String.format("%04d", new Random().nextInt(10000));
        user.setVerificationStatus(EVerificationStatus.PENDING);
        user.setVerificationCode(verificationCode);
        this.userRepository.persist(user);
        mailService.sendInitiateEmailVerificationMail(user.getEmail(), user.getNames(), verificationCode);
        return "Password reset code sent to email";
    }

    @Override
    public String verifyEmail(String token) {
        User user = this.userRepository.findByVerificationCode(token);
        if (user == null) throw new CustomBadRequestException("Invalid token");
        user.setVerificationStatus(EVerificationStatus.VERIFIED);
        user.setVerificationCode(null);
        this.userRepository.persist(user);
        this.mailService.sendEmailVerifiedSuccessfullyMail(user.getEmail(), user.getNames());
        return "Email verified successfully";
    }
}
