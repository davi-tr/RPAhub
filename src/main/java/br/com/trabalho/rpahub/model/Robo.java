package br.com.trabalho.rpahub.model;

import br.com.trabalho.rpahub.dto.RegisterRoboDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "robos")
public class Robo implements Serializable {
    @Id
    @GeneratedValue ( strategy = GenerationType.UUID)
    private UUID id;

    private String nome;
    private String descricao;
    private String areaSolicitante;
    @ManyToOne
    @JoinColumn(name = "responsavel_id")
    private User responsavel;
    @ManyToMany
    @JoinTable(name = "robos_vm",
            joinColumns = @JoinColumn(name = "robo_id"),
            inverseJoinColumns = @JoinColumn(name = "vm_id"))
    @JsonManagedReference
    private List<VM> maquinaAlocada = new ArrayList<>();
    public Robo() {
    }
    public Robo(String nome, String descricao, String areaSolicitante, User responsavel) {
        this.nome = nome;
        this.descricao = descricao;
        this.areaSolicitante = areaSolicitante;
        this.responsavel = responsavel;
    }

    public Robo(RegisterRoboDTO dadosDTO, User responsavel) {
        this.nome = dadosDTO.nome();
        this.descricao = dadosDTO.descricao();
        this.areaSolicitante = dadosDTO.areaSolicitante();
        this.responsavel = responsavel;
    }

    public void setMaquinaAlocada(VM maquina) {
        this.maquinaAlocada.add(maquina);
    }
}
