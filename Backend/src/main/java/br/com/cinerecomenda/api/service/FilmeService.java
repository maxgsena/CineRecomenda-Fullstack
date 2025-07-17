package br.com.cinerecomenda.api.service;

import br.com.cinerecomenda.api.model.Filme;
import br.com.cinerecomenda.api.model.FilmeComNota;
import br.com.cinerecomenda.api.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private StorageService storageService;

    public List<Filme> listarTodos() {
        return filmeRepository.findAll();
    }

    public Filme salvarFilme(Filme filme) {
        return filmeRepository.save(filme);
    }

    public Filme buscarPorId(Long id) {
        return filmeRepository.findById(id).orElseThrow(() -> new RuntimeException("Filme não encontrado!"));
    }

    public List<FilmeComNota> getFilmesComNota() {
        return filmeRepository.findFilmesComNota();
    }

    public void deletarFilme(Long id) {
        filmeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme com ID " + id + " não encontrado para deleção!"));

        filmeRepository.deleteById(id);
    }

    public Filme atualizarPoster(Long idFilme, MultipartFile posterFile) {
        String fileName = storageService.store(posterFile);
        Filme filme = buscarPorId(idFilme);
        filme.setPosterFileName(fileName);

        return filmeRepository.save(filme);
    }
}
