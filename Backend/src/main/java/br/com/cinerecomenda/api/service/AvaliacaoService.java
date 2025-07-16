package br.com.cinerecomenda.api.service;

import br.com.cinerecomenda.api.model.Avaliacao;
import br.com.cinerecomenda.api.model.Filme;
import br.com.cinerecomenda.api.model.Usuario;
import br.com.cinerecomenda.api.repository.AvaliacaoRepository;
import br.com.cinerecomenda.api.repository.FilmeRepository;
import br.com.cinerecomenda.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoService {


    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FilmeRepository filmeRepository;


    public List<Avaliacao> buscarPorFilme(Long idFilme) {
        return avaliacaoRepository.findByFilmeIdFilme(idFilme);
    }


    public Avaliacao criar(Avaliacao avaliacao, Long idUsuario, Long idFilme) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        Filme filme = filmeRepository.findById(idFilme)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado!"));


        avaliacao.setUsuario(usuario);
        avaliacao.setFilme(filme);


        return avaliacaoRepository.save(avaliacao);
    }
}