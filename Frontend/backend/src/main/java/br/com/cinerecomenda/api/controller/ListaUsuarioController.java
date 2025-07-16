package br.com.cinerecomenda.api.controller;

import br.com.cinerecomenda.api.model.ListaUsuario;
import br.com.cinerecomenda.api.service.ListaUsuarioService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/usuario/{idUsuario}")
    public List<ListaUsuario> buscarPorUsuario(@PathVariable Long idUsuario) {
        return listaUsuarioService.buscarListaDoUsuario(idUsuario);
    }
}


@Data
class ListaUsuarioRequestDTO {
    private Long idUsuario;
    private Long idFilme;
    private String estado;
}