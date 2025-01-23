package br.com.trabalho.rpahub.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateVmDTO(
        @NotBlank String id,
        String nome,
        String ip
) {
}
