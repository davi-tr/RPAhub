package br.com.trabalho.rpahub.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateRoboDTO(
        @NotBlank String id,
        String nome,
        String descricao,
        String areaSolicitante,
        String emailResponsavel,
        String maquinaAlocada,
        String maquinaDesalocada
) {
}
