package br.com.trabalho.rpahub.controller;

import br.com.trabalho.rpahub.dto.RegisterRoboDTO;
import br.com.trabalho.rpahub.repository.RoboRepository;
import br.com.trabalho.rpahub.repository.UserRepository;
import br.com.trabalho.rpahub.service.RoboService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/robo")
public class RoboController {
    @Autowired
    private RoboService roboService;

    @PostMapping("/registerNew")
    public ResponseEntity<?> registerNew(@RequestBody @Valid RegisterRoboDTO registerRoboDTO){
        return roboService.registerNew(registerRoboDTO);
    }
    @GetMapping
    public ResponseEntity<?> getRobos(@PageableDefault(direction = Sort.Direction.DESC, size = Integer.MAX_VALUE)
    Pageable paginacao){
        return roboService.getRobos(paginacao);
    }
}
