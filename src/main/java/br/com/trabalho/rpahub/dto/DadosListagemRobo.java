package br.com.trabalho.rpahub.dto;

import br.com.trabalho.rpahub.model.Robo;
import br.com.trabalho.rpahub.model.VM;

import java.util.List;

public record DadosListagemRobo(String nome, String descricao, String areaSolicitante, String emailResponsavel, List<VM> maquinaAlocada) {
    public DadosListagemRobo(Robo robo) {
        this(robo.getNome(), robo.getDescricao(), robo.getAreaSolicitante(), robo.getResponsavel().getEmail(), robo.getMaquinaAlocada());

    }

}
