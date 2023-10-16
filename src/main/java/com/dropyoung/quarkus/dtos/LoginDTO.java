package com.dropyoung.quarkus.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
public class LoginDTO {

    @Email(message = "Email should be valid")
    @Schema(example = "precieuxmugisha@gmail.com")
    private String email;

    @Schema(example = "Password.123")
    @Min(value = 6, message = "Password must have at least 6 characters")
    @Pattern(regexp = "/^(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[A-Za-z\\d\\W_]{6,}$/", message = "Password must have at least 6 characters, one symbol, one number, and one uppercase letter.")
    private String password;

}
