package br.com.cinerecomenda.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "genero")
@Data
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGenero;

    private String nome;
}