package br.com.cinerecomenda.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
@Table(name = "usuario")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String nome;
    private String email;
    private String senha;
    private String generoPref;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private Set<Avaliacao> avaliacoes;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private Set<ListaUsuario> itensDaLista;
}