package br.com.cinerecomenda.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "filmes")
@Data
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFilme;

    private String nome;
    private Date anoLanc;
    private String sinopse;
    private String duracao;
    private String posterFileName;

    // Campo calculado dinamicamente, não salvo no banco
    @Transient
    public String getPosterUrl() {
        if (posterFileName == null || posterFileName.isEmpty()) return null;
        return "/images/" + posterFileName;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "filme_generos",
            joinColumns = @JoinColumn(name = "id_filme"),
            inverseJoinColumns = @JoinColumn(name = "id_genero")
    )
    private Set<Genero> generos = new HashSet<>();

    @OneToMany(mappedBy = "filme")
    @JsonIgnore
    private Set<Avaliacao> avaliacoes;

    @OneToMany(mappedBy = "filme")
    @JsonIgnore
    private Set<ListaUsuario> aparicoesEmListas;
}
