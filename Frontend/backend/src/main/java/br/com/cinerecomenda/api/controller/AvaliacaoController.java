package br.com.cinerecomenda.api.controller;

import br.com.cinerecomenda.api.model.Avaliacao;
import br.com.cinerecomenda.api.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @GetMapping("/filme/{idFilme}")
    public List<Avaliacao> getAvaliacoesPorFilme(@PathVariable Long idFilme) {
        return avaliacaoService.buscarPorFilme(idFilme);
    }


    @PostMapping
    public Avaliacao criarAvaliacao(@RequestBody Avaliacao avaliacao, @RequestParam Long idUsuario, @RequestParam Long idFilme) {
        return avaliacaoService.criar(avaliacao, idUsuario, idFilme);
    }
}