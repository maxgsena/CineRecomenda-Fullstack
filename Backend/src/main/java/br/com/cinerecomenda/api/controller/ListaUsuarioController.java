package br.com.cinerecomenda.api.controller;

import br.com.cinerecomenda.api.model.ListaUsuario;
import br.com.cinerecomenda.api.service.ListaUsuarioService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/listas")
public class ListaUsuarioController {

    @Autowired
    private ListaUsuarioService listaUsuarioService;

    @PostMapping
    public ListaUsuario adicionar(@RequestBody ListaUsuarioRequestDTO request) {
        return listaUsuarioService.adicionarFilmeNaLista(request.getIdUsuario(), request.getIdFilme(), request.getEstado());
    }

    @PutMapping
    public ResponseEntity<Void> atualizar(@RequestBody ListaUsuarioRequestDTO request) {
        listaUsuarioService.atualizarEstadoFilme(request.getIdUsuario(), request.getIdFilme(), request.getEstado());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> remover(@RequestParam Long idUsuario, @RequestParam Long idFilme) {
        listaUsuarioService.removerFilmeDaLista(idUsuario, idFilme);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/usuario/{idUsuario}")
    public List<ListaUsuario> buscarPorUsuario(@PathVariable Long idUsuario) {
        return listaUsuarioService.buscarListaDoUsuario(idUsuario);
    }
}

// Classe auxiliar (DTO)
@Data
class ListaUsuarioRequestDTO {
    private Long idUsuario;
    private Long idFilme;
    private String estado;
}