package br.com.trabalho.rpahub.model;

import br.com.trabalho.rpahub.dto.RegisterUserDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data  
@Getter
@Entity
@Table(name = "users")
public class User implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;
    @Column(unique = true)
    private String email;
    @Setter
    private String password;
    @Column(unique = true)
    private String matricula;
    private String cargo;
    private Boolean enabled;

    public User(String nome, String email, String password, String matricula, String cargo) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.matricula = matricula;
        this.cargo = cargo;
        this.enabled = true;
    }
    public User(){

    }
    public User(RegisterUserDTO dadosDTO){
        this.nome = dadosDTO.nome();
        this.email = dadosDTO.email();
        this.password = dadosDTO.password();
        this.matricula = dadosDTO.matricula();
        this.cargo = dadosDTO.cargo();
        this.enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }


    public String getEmail() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
