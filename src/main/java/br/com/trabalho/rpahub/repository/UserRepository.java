package br.com.trabalho.rpahub.repository;

import br.com.trabalho.rpahub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    UserDetails findByEmail(String email);

    UserDetails findByMatricula(String matricula);
}
