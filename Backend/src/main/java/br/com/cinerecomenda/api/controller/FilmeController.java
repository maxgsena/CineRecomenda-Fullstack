package br.com.cinerecomenda.api.controller;

import br.com.cinerecomenda.api.model.Filme;
import br.com.cinerecomenda.api.model.FilmeComNota;
import br.com.cinerecomenda.api.repository.FilmeRepository;
import br.com.cinerecomenda.api.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private FilmeRepository filmeRepository;

    @GetMapping
    public List<Filme> listarTodosOsFilmes() {
        return filmeService.listarTodos();
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Filme> adicionarFilmeComPoster(
            @RequestPart("filme") Filme filme,
            @RequestPart(value = "poster", required = false) MultipartFile poster) {

        try {
            if (poster != null && !poster.isEmpty()) {
                String folder = new ClassPathResource("static/images").getFile().getAbsolutePath();
                String posterFileName = poster.getOriginalFilename();

                Path path = Paths.get(folder + File.separator + posterFileName);
                Files.write(path, poster.getBytes());

                filme.setPosterFileName(posterFileName);

            }

            Filme filmeSalvo = filmeService.salvarFilme(filme);
            return ResponseEntity.ok(filmeSalvo);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filme> getFilmePorId(@PathVariable Long id) {
        try {
            Filme filme = filmeService.buscarPorId(id);
            return ResponseEntity.ok(filme);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/com-notas")
    public List<FilmeComNota> getFilmesComNotas() {
        return filmeService.getFilmesComNota();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFilme(@PathVariable Long id) {
        filmeService.deletarFilme(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/poster")
    public ResponseEntity<String> uploadPoster(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        Optional<Filme> optionalFilme = filmeRepository.findById(id);
        if (!optionalFilme.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Filme filme = optionalFilme.get();

        try {
            String folder = new ClassPathResource("static/images").getFile().getAbsolutePath();
            String originalFileName = file.getOriginalFilename();

            if (originalFileName == null || originalFileName.isBlank()) {
                return ResponseEntity.badRequest().body("Nome de arquivo inválido.");
            }

            Path path = Paths.get(folder + File.separator + originalFileName);
            Files.write(path, file.getBytes());

            filme.setPosterFileName(originalFileName);

            filmeRepository.save(filme);

            return ResponseEntity.ok("Pôster salvo com sucesso!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o pôster.");
        }
    }
}
