package br.com.trabalho.rpahub.service;

import br.com.trabalho.rpahub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service

public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public ResponseEntity<?> deleteUser(String matricula){
        var userMat = userRepository.findByMatricula(matricula);

        var user = userRepository.findUserByEmail(userMat.getUsername());
        if (user == null){
            return ResponseEntity.badRequest().body("Usuario não encontrado");
        }
        try {
            user.setEnabled(false);
            userRepository.save(user);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Erro ao deletar usuario");
        }

        return ResponseEntity.ok().body("Usuario deletado com sucesso");
    }
}
