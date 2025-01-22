package br.com.trabalho.rpahub.service;

import br.com.trabalho.rpahub.dto.DadosListagemRobo;
import br.com.trabalho.rpahub.dto.RegisterRoboDTO;
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

    public ResponseEntity<?> getRobos(@PageableDefault (direction = Sort.Direction.DESC, size = Integer.MAX_VALUE) Pageable paginacao) {
        var page = roboRepository.findAll(paginacao).map(DadosListagemRobo::new);
        return ResponseEntity.ok(page);
    }
}
