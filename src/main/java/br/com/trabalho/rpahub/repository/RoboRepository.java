package br.com.trabalho.rpahub.repository;

import br.com.trabalho.rpahub.model.Robo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoboRepository extends JpaRepository<Robo, UUID> {
    Optional<Robo> findById(UUID id);
    Optional<Robo> findByNome(String nome);
}
