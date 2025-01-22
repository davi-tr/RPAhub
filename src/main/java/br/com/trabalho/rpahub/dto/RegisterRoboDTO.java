package br.com.trabalho.rpahub.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRoboDTO(
        @NotBlank String nome,
        @NotBlank String descricao,
        @NotBlank String areaSolicitante,
        @NotBlank String emailResponsavel,
        String maquinaAlocada
) {
}
