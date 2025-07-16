package br.com.cinerecomenda.api.repository;

import br.com.cinerecomenda.api.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    List<Avaliacao> findByFilmeIdFilme(Long idFilme);
    List<Avaliacao> findByUsuarioIdUsuario(Long idUsuario);
}