package br.com.cinerecomenda.api.service;

import br.com.cinerecomenda.api.model.Genero;
import br.com.cinerecomenda.api.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    public List<Genero> listarTodos() {
        return generoRepository.findAll();
    }

    public Genero salvar(Genero genero) {
        return generoRepository.save(genero);
    }
}