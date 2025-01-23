package br.com.trabalho.rpahub.controller;

import br.com.trabalho.rpahub.dto.RegisterRoboDTO;
import br.com.trabalho.rpahub.dto.UpdateRoboDTO;
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

import java.util.UUID;

@RestController
@RequestMapping("/rpa")
public class RoboController {
    @Autowired
    private RoboService roboService;

    @PostMapping("/createFlow")
    public ResponseEntity<?> createFlow(@RequestBody @Valid RegisterRoboDTO registerRoboDTO){
        return roboService.registerNew(registerRoboDTO);
    }
    @GetMapping("/getFlows")
    public ResponseEntity<?> getFlows(@PageableDefault(direction = Sort.Direction.DESC, size = Integer.MAX_VALUE)
    Pageable paginacao){
        return roboService.getFlows(paginacao);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRobo(@PathVariable String id){
        return roboService.deleteFlow(UUID.fromString(id));
    }
    @PutMapping("/updateFlow")
    public ResponseEntity<?> updateFlow(@RequestBody @Valid UpdateRoboDTO updateRoboDTO){
        return roboService.updateFlow(updateRoboDTO);
    }
}
