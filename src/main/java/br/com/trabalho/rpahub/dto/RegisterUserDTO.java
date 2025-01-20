package br.com.trabalho.rpahub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record RegisterUserDTO(
        @NotBlank @Email String email,
        @NotBlank String nome,
        @NotBlank String matricula,
        @NotBlank String password,
        @NotBlank String cargo
) implements Serializable {
}
