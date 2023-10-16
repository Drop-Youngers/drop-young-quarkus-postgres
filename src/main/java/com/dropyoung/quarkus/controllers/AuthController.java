package com.dropyoung.quarkus.controllers;

import com.dropyoung.quarkus.dtos.InitiateResetPasswordDTO;
import com.dropyoung.quarkus.dtos.LoginDTO;
import com.dropyoung.quarkus.dtos.ResetPasswordDTO;
import com.dropyoung.quarkus.payload.ApiResponse;
import com.dropyoung.quarkus.services.IAuthService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.UUID;

@Tag(ref = "Auth")
@Path("auth")
@RequiredArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {

    private final IAuthService authService;

    @POST
    @Path("login")
    public Response login(
            @Valid LoginDTO dto
    ) {
        return Response.ok(ApiResponse.success("Logged in successfully", this.authService.login(dto.getEmail(), dto.getPassword()))).build();
    }

    @POST
    @Transactional
    @Path("initiate-reset-password")
    public Response initiateResetPassword(
            @Valid InitiateResetPasswordDTO dto
    ) {
        return Response.ok(ApiResponse.success("Reset password email sent successfully", this.authService.initiatePasswordReset(dto.getEmail()))).build();
    }

    @PATCH
    @Transactional
    @Path("reset-password")
    public Response resetPassword(
            @Valid ResetPasswordDTO dto
    ) {
        return Response.ok(ApiResponse.success("Password reset successfully", this.authService.resetPassword(dto.getToken(), dto.getPassword()))).build();
    }

    @POST
    @Transactional
    @Path("initiate-email-verification")
    @RolesAllowed({"ADMIN", "NORMAL"})
    public Response initiateEmailVerification(
            @Context SecurityContext securityContext
    ) {
        return Response.ok(ApiResponse.success("Email verification email sent successfully", this.authService.initiateEmailVerification(UUID.fromString(securityContext.getUserPrincipal().getName())))).build();
    }

    @PATCH
    @Transactional
    @Path("verify-email")
    public Response verifyEmail(
            @QueryParam("token") String token
    ) {
        return Response.ok(ApiResponse.success("Email verified successfully", this.authService.verifyEmail(token))).build();
    }

}
