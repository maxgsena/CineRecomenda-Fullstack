package br.com.cinerecomenda.api.controller;

import br.com.cinerecomenda.api.model.Filme;
import br.com.cinerecomenda.api.model.FilmeComNota;
import br.com.cinerecomenda.api.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    // --- MÉTODO QUE FALTAVA ---
    // Este endpoint irá lidar com requisições como GET /filmes/1, GET /filmes/2, etc.
    @GetMapping("/{id}")
    public ResponseEntity<Filme> getFilmePorId(@PathVariable Long id) {
        try {
            Filme filme = filmeService.buscarPorId(id);
            return ResponseEntity.ok(filme); // Retorna 200 OK com os dados do filme
        } catch (RuntimeException e) {
            // Se o serviço não encontrar o filme, retorna 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/com-notas")
    public List<FilmeComNota> getFilmesComNotas() {
        return filmeService.getFilmesComNota();
    }
}