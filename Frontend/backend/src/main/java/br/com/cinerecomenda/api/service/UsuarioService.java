package br.com.cinerecomenda.api.service;

import br.com.cinerecomenda.api.model.Usuario;
import br.com.cinerecomenda.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvar(Usuario usuario) {

        return usuarioRepository.save(usuario);
    }

    public boolean validarLogin(Usuario usuario) {

        return usuarioRepository.findByEmailAndSenha(usuario.getEmail(), usuario.getSenha()).isPresent();
    }
}