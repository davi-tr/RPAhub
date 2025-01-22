package br.com.trabalho.rpahub.repository;

import br.com.trabalho.rpahub.model.VM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VmRepository extends JpaRepository<VM, UUID> {
    Optional<VM> findById(UUID id);

    VM findByNome(String nome);

    VM findByIp(String ip);
}
