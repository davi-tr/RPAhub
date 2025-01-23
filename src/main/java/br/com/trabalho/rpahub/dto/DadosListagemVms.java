package br.com.trabalho.rpahub.dto;

import br.com.trabalho.rpahub.model.VM;

import java.util.UUID;

public record DadosListagemVms(
        UUID id,
        String nome,
        String ip
) {
    public DadosListagemVms (VM vm) {
        this(vm.getId(),vm.getNome(), vm.getIp());
    }
}
