package com.dropyoung.quarkus.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
public class ResetPasswordDTO {

    @Min(6)
    @Max(6)
    @Schema(example = "234356")
    private String token;

    @Schema(example = "Password.123")
    @Min(value = 6, message = "Password must have at least 6 characters")
    @Pattern(regexp = "/^(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[A-Za-z\\d\\W_]{6,}$/", message = "Password must have at least 6 characters, one symbol, one number, and one uppercase letter.")
    private String password;

}
