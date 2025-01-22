package br.com.trabalho.rpahub.dto;

import br.com.trabalho.rpahub.model.VM;

public record DadosListagemVms(
        String nome,
        String ip
) {
    public DadosListagemVms (VM vm) {
        this(vm.getNome(), vm.getIp());
    }
}
