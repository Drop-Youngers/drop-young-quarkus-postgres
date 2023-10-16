package com.dropyoung.quarkus.dtos;

import com.dropyoung.quarkus.enums.EGender;
import com.dropyoung.quarkus.enums.ERole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Set;

@Data
public class CreateUserDTO {

    @Schema(example = "Mugisha Precieux")
    @NotEmpty(message = "Names should not be empty")
    private String names;

    @Email(message = "Email should be valid")
    @Schema(example = "precieuxmugisha@gmail.com")
    private String email;

    @Schema(example = "MALE")
    private EGender gender;

    @Schema(example = "NORMAL")
    private ERole role;

    @Schema(example = "+250782307144")
    @Pattern(regexp = "/^\\+250\\d{9}$/", message = "Telephone starts with +250 and has 9 digits after.")
    private String telephone;

    @Schema(example = "Password.123")
    @Min(value = 6, message = "Password must have at least 6 characters")
    @Pattern(regexp = "/^(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[A-Za-z\\d\\W_]{6,}$/", message = "Password must have at least 6 characters, one symbol, one number, and one uppercase letter.")
    private String password;


}
