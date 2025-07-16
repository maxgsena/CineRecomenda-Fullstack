package br.com.cinerecomenda.api.controller;

import br.com.cinerecomenda.api.model.Usuario;
import br.com.cinerecomenda.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")

public class UsuarioController {


    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public Usuario criar(@RequestBody Usuario usuario) {

        return usuarioService.salvar(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {

        Optional<Usuario> usuarioValidado = usuarioService.validarLogin(usuario.getEmail(), usuario.getSenha());

        if (usuarioValidado.isPresent()) {
            return ResponseEntity.ok(usuarioValidado.get());
        } else {
            return ResponseEntity.status(401).body("Email ou senha inválidos.");
        }
    }
}