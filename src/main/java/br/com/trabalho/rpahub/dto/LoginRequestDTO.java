package br.com.trabalho.rpahub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record LoginRequestDTO(
        @NotBlank @Email String email,
        @NotBlank String password
) implements Serializable {
}
