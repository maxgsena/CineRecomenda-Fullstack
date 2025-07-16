package br.com.cinerecomenda.api.service;

import br.com.cinerecomenda.api.model.Filme;
import br.com.cinerecomenda.api.model.ListaUsuario;
import br.com.cinerecomenda.api.model.Usuario;
import br.com.cinerecomenda.api.repository.FilmeRepository;
import br.com.cinerecomenda.api.repository.ListaUsuarioRepository;
import br.com.cinerecomenda.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListaUsuarioService {

    @Autowired
    private ListaUsuarioRepository listaUsuarioRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private FilmeRepository filmeRepository;

    public ListaUsuario adicionarFilmeNaLista(Long idUsuario, Long idFilme, String estado) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Filme filme = filmeRepository.findById(idFilme).orElseThrow(() -> new RuntimeException("Filme não encontrado"));

        ListaUsuario novoItem = new ListaUsuario();
        novoItem.setUsuario(usuario);
        novoItem.setFilme(filme);
        novoItem.setEstado(estado);

        return listaUsuarioRepository.save(novoItem);
    }

    public List<ListaUsuario> buscarListaDoUsuario(Long idUsuario) {
        return listaUsuarioRepository.findByUsuarioIdUsuario(idUsuario);
    }
}