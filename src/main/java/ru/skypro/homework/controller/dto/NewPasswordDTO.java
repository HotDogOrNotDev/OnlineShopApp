package ru.skypro.homework.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Schema(name = "NewPassword")
public class NewPasswordDTO {

    @NotBlank
    @Size(min = 8, max = 16)
    private String currentPassword;
    @NotBlank
    @Size(min = 8, max = 16)
    private String newPassword;
}
