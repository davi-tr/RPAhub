package br.com.trabalho.rpahub.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterVmDTO(
        @NotBlank String nome,
        @NotBlank String ip
) {
}
