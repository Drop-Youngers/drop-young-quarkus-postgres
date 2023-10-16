package com.dropyoung.quarkus.dtos;

import com.dropyoung.quarkus.enums.EGender;
import com.dropyoung.quarkus.enums.ERole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
public class UpdateUserDTO {


    @Schema(example = "Mugisha Precieux")
    @NotEmpty(message = "Names should not be empty")
    private String names;

    @Email(message = "Email should be valid")
    @Schema(example = "precieuxmugisha@gmail.com")
    private String email;

    @Schema(example = "MALE")
    private EGender gender;

    @Schema(example = "+250782307144")
    @Pattern(regexp = "/^\\+250\\d{9}$/", message = "Telephone starts with +250 and has 9 digits after.")
    private String telephone;

}
