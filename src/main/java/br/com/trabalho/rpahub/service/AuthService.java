package br.com.trabalho.rpahub.service;

import br.com.trabalho.rpahub.dto.LoginRequestDTO;
import br.com.trabalho.rpahub.dto.LoginResponseDTO;
import br.com.trabalho.rpahub.dto.RegisterUserDTO;
import br.com.trabalho.rpahub.model.User;
import br.com.trabalho.rpahub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenService tokenService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;

    public ResponseEntity<?> login(LoginRequestDTO loginRequestDTO){
        var user = userRepository.findByEmail(loginRequestDTO.email());
        try{
            passwordEncoder.matches(loginRequestDTO.password(), user.getPassword());
            var token = tokenService.generateToken((User) user);
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Senha ou email invalido");
        }

    }

    public ResponseEntity<?> register(RegisterUserDTO registerUserDTO){
        if(userRepository.findByEmail(registerUserDTO.email()) != null){
            return ResponseEntity.badRequest().body("Email already in use");
        }
        String encrypterPassword = new BCryptPasswordEncoder().encode(registerUserDTO.password());
        User newUser = new User(registerUserDTO.nome(),registerUserDTO.email(),encrypterPassword, registerUserDTO.matricula(), registerUserDTO.cargo() );
        userRepository.save(newUser);
        return ResponseEntity.noContent().build();
    }
}
