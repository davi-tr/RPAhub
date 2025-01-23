package br.com.trabalho.rpahub.service;

import br.com.trabalho.rpahub.dto.DadosListagemRobo;
import br.com.trabalho.rpahub.dto.RegisterRoboDTO;
import br.com.trabalho.rpahub.dto.UpdateRoboDTO;
import br.com.trabalho.rpahub.model.Robo;
import br.com.trabalho.rpahub.model.User;
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

import java.util.List;
import java.util.UUID;

@Service
public class RoboService {
    @Autowired
    RoboRepository roboRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VmRepository vmRepository;

    public ResponseEntity<?> registerNew(RegisterRoboDTO registerRoboDTO) {
        var user = userRepository.findByEmail(registerRoboDTO.emailResponsavel());
        if (user == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        if (roboRepository.findByNome(registerRoboDTO.nome()).isPresent()) {
            return ResponseEntity.badRequest().body("Robô já cadastrado");
        }
        var robo = new Robo(registerRoboDTO.nome(), registerRoboDTO.descricao(), registerRoboDTO.areaSolicitante(), (User) user);
        if (registerRoboDTO.maquinaAlocada() != null) {
            var vm = vmRepository.findByIp(registerRoboDTO.maquinaAlocada());
            if (vm != null) {
                robo.setMaquinaAlocada(vm);
            } else {
                return ResponseEntity.badRequest().body("Máquina não encontrada");
            }
        }
        roboRepository.save(robo);
        return ResponseEntity.ok().body("Robô cadastrado com sucesso");
    }

    public ResponseEntity<?> getFlows(@PageableDefault (direction = Sort.Direction.DESC, size = Integer.MAX_VALUE) Pageable paginacao) {
        var page = roboRepository.findAll(paginacao).map(DadosListagemRobo::new);
        return ResponseEntity.ok(page);
    }

    public ResponseEntity<?> deleteFlow(UUID id){
        var robo = roboRepository.findById(id);
        if(robo.isEmpty()){
            return ResponseEntity.badRequest().body("Robô não encontrado");
        }
        roboRepository.delete(robo.get());
        return ResponseEntity.ok().body("Robô deletado com sucesso");
    }

    public ResponseEntity<?> updateFlow(UpdateRoboDTO dadosDTO){
        var robo = roboRepository.findById(UUID.fromString(dadosDTO.id()));
        if(robo.isEmpty()){
            return ResponseEntity.badRequest().body("Robô não encontrado");
        }
        if(dadosDTO.nome() != null){
            if(roboRepository.findByNome(dadosDTO.nome()).isPresent()){
                return ResponseEntity.badRequest().body("Robô já cadastrado");
            }
            robo.get().setNome(dadosDTO.nome());
        }
        if (dadosDTO.descricao() != null){
            robo.get().setDescricao(dadosDTO.descricao());
        }
        if (dadosDTO.areaSolicitante() != null){
            robo.get().setAreaSolicitante(dadosDTO.areaSolicitante());
        }
        if (dadosDTO.emailResponsavel() != null){
            var user = userRepository.findByEmail(dadosDTO.emailResponsavel());
            if(user == null){
                return ResponseEntity.badRequest().body("Usuário não encontrado");
            }
            robo.get().setResponsavel((User) user);
        }
        if(dadosDTO.maquinaAlocada() != null){
            var vm = vmRepository.findByIp(dadosDTO.maquinaAlocada());
            if(vm == null){
                return ResponseEntity.badRequest().body("Máquina não encontrada");
            }
            if(robo.get().getMaquinaAlocada() != null){
                for(VM v : robo.get().getMaquinaAlocada()){
                    if (v.getIp().equals(vm.getIp())){
                        return ResponseEntity.badRequest().body("Máquina já alocada");
                    }
                }
            }
            robo.get().setMaquinaAlocada(vm);
        }
        if(dadosDTO.maquinaDesalocada() != null){
            var vm = vmRepository.findByIp(dadosDTO.maquinaDesalocada());
            if(vm == null){
                return ResponseEntity.badRequest().body("Máquina não encontrada");
            }
            if(!robo.get().getMaquinaAlocada().contains(vm)){
                return ResponseEntity.badRequest().body("Máquina não alocada");
            }
            robo.get().desalocarMaquina(vm);
        }
        try {
            roboRepository.save(robo.get());
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Erro ao atualizar o robô");
        }
        return ResponseEntity.ok().body("Robô atualizado com sucesso");
    }
}