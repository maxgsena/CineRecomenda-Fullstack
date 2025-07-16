package br.com.cinerecomenda.api.controller;

import br.com.cinerecomenda.api.model.Filme;
import br.com.cinerecomenda.api.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    @GetMapping
    public List<Filme> listarTodosOsFilmes() {

        return filmeService.listarTodos();
    }

    @PostMapping
    public Filme adicionarFilme(@RequestBody Filme filme) {

        return filmeService.salvarFilme(filme);
    }
}