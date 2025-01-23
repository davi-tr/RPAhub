package br.com.trabalho.rpahub.dto;

import br.com.trabalho.rpahub.model.Robo;
import br.com.trabalho.rpahub.model.VM;

import java.util.List;
import java.util.UUID;

public record DadosListagemRobo(UUID id, String nome, String descricao, String areaSolicitante, String emailResponsavel, List<VM> maquinaAlocada) {
    public DadosListagemRobo(Robo robo) {
        this(robo.getId(),robo.getNome(), robo.getDescricao(), robo.getAreaSolicitante(), robo.getResponsavel().getEmail(), robo.getMaquinaAlocada());

    }

}
