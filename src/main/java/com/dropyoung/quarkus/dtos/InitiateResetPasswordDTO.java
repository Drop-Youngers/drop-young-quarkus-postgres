package com.dropyoung.quarkus.dtos;

import jakarta.validation.constraints.Email;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
public class InitiateResetPasswordDTO {

    @Email(message = "Email should be valid")
    @Schema(example = "precieuxmugisha@gmail.com")
    private String email;

}
