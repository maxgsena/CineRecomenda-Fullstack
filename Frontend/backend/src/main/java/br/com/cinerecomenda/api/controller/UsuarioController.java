package br.com.cinerecomenda.api.controller;

import br.com.cinerecomenda.api.model.Usuario;
import br.com.cinerecomenda.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        boolean loginValido = usuarioService.validarLogin(usuario);
        if (loginValido) {
            return ResponseEntity.ok("Login bem-sucedido!");
        } else {
            return ResponseEntity.status(401).body("Email ou senha inválidos."); // 401 Unauthorized
        }
    }
}