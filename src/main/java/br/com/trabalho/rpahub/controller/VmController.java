package br.com.trabalho.rpahub.controller;

import br.com.trabalho.rpahub.dto.RegisterVmDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.trabalho.rpahub.service.VmService;

@RestController
@RequestMapping("/vm")
public class VmController {
    @Autowired
    private VmService vmService;

    @PostMapping("/createVm")
    public ResponseEntity<?> registerNew(@RequestBody @Valid RegisterVmDTO registerVmDTO){
        return vmService.registerNew(registerVmDTO);
    }
    @GetMapping("/getVms")
    public ResponseEntity<?> getVms(@PageableDefault(direction = Sort.Direction.DESC, size = Integer.MAX_VALUE) Pageable paginacao){
        return vmService.getVms(paginacao);
    }
}
