package br.com.trabalho.rpahub.service;

import br.com.trabalho.rpahub.dto.DadosListagemVms;
import br.com.trabalho.rpahub.dto.RegisterVmDTO;
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

@Service
public class VmService {
    @Autowired
    RoboRepository roboRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VmRepository vmRepository;

    public ResponseEntity<?> registerNew(RegisterVmDTO dadosDTO) {
        if(vmRepository.findByIp(dadosDTO.ip()) != null){
            return ResponseEntity.badRequest().body("Máquina já cadastrada");
        }
        var vm = new VM(dadosDTO.nome(), dadosDTO.ip());
        vmRepository.save(vm);
        return ResponseEntity.ok().body("Máquina cadastrada com sucesso");
    }
    public ResponseEntity<?> getVms(@PageableDefault (direction = Sort.Direction.DESC, size = Integer.MAX_VALUE)Pageable paginacao){
        var page = vmRepository.findAll(paginacao).map(DadosListagemVms::new);
        return ResponseEntity.ok(page);
    }

}
