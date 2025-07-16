package br.com.cinerecomenda.api.service;

import br.com.cinerecomenda.api.model.Usuario;
import br.com.cinerecomenda.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> validarLogin(String email, String senha) {
        return usuarioRepository.findByEmailAndSenha(email, senha);
    }
}