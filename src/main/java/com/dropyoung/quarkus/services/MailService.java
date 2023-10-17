package com.dropyoung.quarkus.services;

import com.dropyoung.quarkus.exceptions.CustomInternalServerException;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class MailService {
    @Inject
    Mailer mailer;


    public void sendInitiateResetPasswordMail(String toEmail, String names, String activationCodes) {
        try {
            mailer.send(
                    Mail.withText(toEmail,
                            "Quarkus Starter Reset Password Code",
                            "Dear " + names + "!\n" +
                                    "\n" +
                                    "You've requested to reset password to spring-starter, " +
                                    "your verification code is " + activationCodes + ". \n" +
                                    "\n" +
                                    "This code expires in 5 hours.\n" +
                                    "\n" +
                                    "If you have any questions, send us an email preciuxmugisha@gma.com.\n" +
                                    "\n" +
                                    "We’re glad you’re here!\n" +
                                    "\n"
                    ));
        } catch (Exception e) {
            throw new CustomInternalServerException(e.getMessage());
        }
    }

    public void sendPasswordResetSuccessfullyMail(String toEmail, String names) {
        try {
            mailer.send(
                    Mail.withText(toEmail,
                            "Quarkus Starter Password Reset Successfully",
                            "Dear " + names + "!\n" +
                                    "\n" +
                                    "You've successfully reset your password to spring-starter. \n" +
                                    "\n" +
                                    "If you have any questions, send us an email"
                    ));
        } catch (Exception e) {
            throw new CustomInternalServerException(e.getMessage());
        }
    }

    public void sendInitiateEmailVerificationMail(String toEmail, String names, String activationCode) {
        try {
            System.out.println("Sending email to " + toEmail);
            mailer.send(
                    Mail.withText(
                            toEmail,
                            "Quarkus Starter Email Verification Code",
                            "Dear " + names + "!\n" +
                                    "\n" +
                                    "You've requested to verify your email to quarkus-starter, " +
                                    "your verification code is " + activationCode + ". \n" +
                                    "\n" +
                                    "This code expires in 2 hours.\n" +
                                    "\n" +
                                    "If you have any questions, send us an email"
                    )
            );
            System.out.println("Email sent to " + toEmail);
        } catch (Exception e) {
            throw new CustomInternalServerException(e.getMessage());
        }
    }

    public void sendEmailVerifiedSuccessfullyMail(String toEmail, String names) {
        try {
            mailer.send(
                    Mail.withText(toEmail,
                            "Quarkus Starter Email Verified Successfully",
                            "Dear " + names + "!\n" +
                                    "\n" +
                                    "You've successfully verified your email to quarkus-starter. \n" +
                                    "\n" +
                                    "If you have any questions, send us an email"
                    ));
        } catch (Exception e) {
            throw new CustomInternalServerException(e.getMessage());
        }
    }

    public void sendWelcomeEmail(String toEmail, String names) {
        try {
            System.out.println("Sending email to " + toEmail);
            mailer.send(
                    Mail.withText(toEmail,
                            "Quarkus Starter Welcome Email",
                            "Dear " + names + "!\n" +
                                    "\n" +
                                    "Welcome to quarkus-starter. \n" +
                                    "\n" +
                                    "If you have any questions, send us an email"
                    ));
        } catch (Exception e) {
            throw new CustomInternalServerException(e.getMessage());
        }
    }

}
