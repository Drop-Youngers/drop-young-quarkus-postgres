package com.dropyoung.quarkus.mappers;

import com.dropyoung.quarkus.exceptions.CustomNotfoundException;
import com.dropyoung.quarkus.payload.ApiResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomNotFoundExceptionMapper implements ExceptionMapper<CustomNotfoundException> {

    @Override
    public Response toResponse(CustomNotfoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(
                        ApiResponse.error(exception.getMessage())
                )
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}