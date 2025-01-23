package br.com.trabalho.rpahub.repository;

import br.com.trabalho.rpahub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.email = ?1 AND u.enabled = true")
    UserDetails findByEmail(String email);

    User findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.matricula = ?1 AND u.enabled = true")
    UserDetails findByMatricula(String matricula);
}
