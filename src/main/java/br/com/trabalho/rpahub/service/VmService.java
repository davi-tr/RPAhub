package br.com.trabalho.rpahub.service;
import br.com.trabalho.rpahub.dto.DadosListagemVms;
import br.com.trabalho.rpahub.dto.RegisterVmDTO;
import br.com.trabalho.rpahub.dto.UpdateVmDTO;
import br.com.trabalho.rpahub.model.VM;
import br.com.trabalho.rpahub.repository.RoboRepository;
import br.com.trabalho.rpahub.repository.UserRepository;
import br.com.trabalho.rpahub.repository.VmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VmService {
    @Autowired
    RoboRepository roboRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VmRepository vmRepository;

    public ResponseEntity<?> registerNew(RegisterVmDTO dadosDTO) {
        if (vmRepository.findByIp(dadosDTO.ip()) != null) {
            return ResponseEntity.badRequest().body("Máquina já cadastrada");
        }
        var vm = new VM(dadosDTO.nome(), dadosDTO.ip());
        vmRepository.save(vm);
        return ResponseEntity.ok().body("Máquina cadastrada com sucesso");
    }

    public ResponseEntity<?> getVms(@PageableDefault(direction = Sort.Direction.DESC, size = Integer.MAX_VALUE) Pageable paginacao) {
        var page = vmRepository.findAll(paginacao).map(DadosListagemVms::new);
        return ResponseEntity.ok(page);
    }

    public ResponseEntity<?> deleteVm(String id) {
        var vm = vmRepository.findById(UUID.fromString(id));
        if (vm.isEmpty()) {
            return ResponseEntity.badRequest().body("Máquina não encontrada");
        }
        if(roboRepository.findByMaquinaAlocada(vm.get()).isPresent()){
            return ResponseEntity.badRequest().body("Máquina alocada a um robô");
        }
        vmRepository.delete(vm.get());
        return ResponseEntity.ok().body("Máquina deletada com sucesso");
    }

    public ResponseEntity<?> updateVm(UpdateVmDTO updateVmDTO) {
        var vm = vmRepository.findById(UUID.fromString(updateVmDTO.id()));
        if (vm.isEmpty()) {
            return ResponseEntity.badRequest().body("Máquina não encontrada");
        }
        if (vmRepository.findByIp(updateVmDTO.ip()) != null) {
            return ResponseEntity.badRequest().body("IP já cadastrado");
        }
        if (vmRepository.findByNome(updateVmDTO.nome()) != null) {
            return ResponseEntity.badRequest().body("Nome da VM já cadastrado");
        }
        if (updateVmDTO.nome() != null) {
            vm.get().setNome(updateVmDTO.nome());
        }
        if (updateVmDTO.ip() != null) {
            vm.get().setIp(updateVmDTO.ip());
        }
        vmRepository.save(vm.get());
        return ResponseEntity.ok().body("Máquina atualizada com sucesso");
    }
}

