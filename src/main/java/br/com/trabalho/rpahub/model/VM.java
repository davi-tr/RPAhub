package br.com.trabalho.rpahub.model;

import br.com.trabalho.rpahub.dto.RegisterVmDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "vms")
public class VM implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String nome;
    private String ip;

    @ManyToMany(mappedBy = "maquinaAlocada")
    @JsonBackReference
    private List<Robo> robos;

    public VM(String nome, String ip) {
        this.nome = nome;
        this.ip = ip;
    }

    public VM(){

    }
    public VM(RegisterVmDTO dadosDTO){
        this.nome = dadosDTO.nome();
        this.ip = dadosDTO.ip();
    }

}
