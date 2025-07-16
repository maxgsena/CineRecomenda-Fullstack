package br.com.cinerecomenda.api.controller;

import br.com.cinerecomenda.api.model.Genero;
import br.com.cinerecomenda.api.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/generos")
public class GeneroController {

    @Autowired
    private GeneroService generoService;

    @GetMapping
    public List<Genero> listar() {
        return generoService.listarTodos();
    }

    @PostMapping
    public Genero criar(@RequestBody Genero genero) {
        return generoService.salvar(genero);
    }
}