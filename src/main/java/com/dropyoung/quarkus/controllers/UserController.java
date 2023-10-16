package com.dropyoung.quarkus.controllers;

import com.dropyoung.quarkus.dtos.CreateUserDTO;
import com.dropyoung.quarkus.dtos.UpdateUserDTO;
import com.dropyoung.quarkus.models.User;
import com.dropyoung.quarkus.payload.ApiResponse;
import com.dropyoung.quarkus.services.IUserService;
import com.dropyoung.quarkus.utils.PasswordEncoder;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.Collections;
import java.util.UUID;

@Tag(ref = "Users")
@Path("users")
@RequiredArgsConstructor
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;

    @POST
    @Path("create")
    public Response createUser(
            @Valid CreateUserDTO dto
    ) {
        User user = new User();
        user.setNames(dto.getNames());
        user.setEmail((dto.getEmail()));
        user.setGender(dto.getGender());
        user.setTelephone(dto.getTelephone());
        user.setRoles(Collections.singleton(dto.getRole()));
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        return Response.status(Response.Status.CREATED).entity(ApiResponse.success("User created successfully", this.userService.create(user))).build();
    }

    @PUT
    @Path("update")
    public Response updateUser(
            @Valid UpdateUserDTO dto,
            @Context SecurityContext ctx
    ) {
        User user = this.userService.findById(UUID.fromString(ctx.getUserPrincipal().getName()));
        user.setNames(dto.getNames());
        user.setEmail((dto.getEmail()));
        user.setGender(dto.getGender());
        user.setTelephone(dto.getTelephone());
        this.userService.update(user);
        return Response.status(Response.Status.CREATED).entity(ApiResponse.success("User updated successfully", user)).build();
    }

    @GET
    @Path("{id}")
    public Response findUserById(
            @PathParam("id") UUID id
    ) {
        User user = this.userService.findById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(ApiResponse.error("User not found")).build();
        }
        return Response.status(Response.Status.OK).entity(ApiResponse.success("User found", user)).build();
    }

    @GET
    @Path("all")
    public Response findAll() {
        return Response.status(Response.Status.OK).entity(ApiResponse.success("Users found", this.userService.findAll())).build();
    }

    @DELETE
    public Response delete(
            @Context SecurityContext ctx
    ){
        this.userService.delete(UUID.fromString(ctx.getUserPrincipal().getName()));
        return Response.status(Response.Status.OK).entity(ApiResponse.success("User deleted successfully")).build();
    }


}
