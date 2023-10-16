package com.dropyoung.quarkus.services;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class MailService {
    @Inject
    ReactiveMailer mailer;


    public void sendInitiateResetPasswordMail(String toEmail, String names, String activationCodes) {
        mailer.send(
                Mail.withHtml(toEmail,
                        "Quarkus Starter Reset Password Code",
                        "Dear " + names + "!\n" +
                                "\n" +
                                "You've requested to reset password to spring-starter, " +
                                "your verification code is " + activationCodes + ". \n" +
                                "\n" +
                                "This code expires in 5 minutes.\n" +
                                "\n" +
                                "If you have any questions, send us an email divin@support.com.\n" +
                                "\n" +
                                "We’re glad you’re here!\n" +
                                "\n"
                ));
    }

    public void sendPasswordResetSuccessfullyMail(String toEmail, String names) {
        mailer.send(
                Mail.withHtml(toEmail,
                        "Quarkus Starter Password Reset Successfully",
                        "Dear " + names + "!\n" +
                                "\n" +
                                "You've successfully reset your password to spring-starter. \n" +
                                "\n" +
                                "If you have any questions, send us an email"
                ));
    }

    public void sendInitiateEmailVerificationMail(String toEmail, String names, String activationCode) {
        mailer.send(
                Mail.withHtml(toEmail,
                        "Quarkus Starter Email Verification Code",
                        "Dear " + names + "!\n" +
                                "\n" +
                                "You've requested to verify your email to quarkus-starter, " +
                                "your verification code is " + activationCode + ". \n" +
                                "\n" +
                                "This code expires in 2 hours.\n" +
                                "\n" +
                                "If you have any questions, send us an email"
                ));
    }

    public void sendEmailVerifiedSuccessfullyMail(String toEmail, String names) {
        mailer.send(
                Mail.withHtml(toEmail,
                        "Quarkus Starter Email Verified Successfully",
                        "Dear " + names + "!\n" +
                                "\n" +
                                "You've successfully verified your email to quarkus-starter. \n" +
                                "\n" +
                                "If you have any questions, send us an email"
                ));
    }

    public void sendWelcomeEmail(String toEmail, String names) {
        mailer.send(
                Mail.withHtml(toEmail,
                        "Quarkus Starter Welcome Email",
                        "Dear " + names + "!\n" +
                                "\n" +
                                "Welcome to quarkus-starter. \n" +
                                "\n" +
                                "If you have any questions, send us an email"
                ));
    }

}
