package br.com.cinerecomenda.api.repository;
import br.com.cinerecomenda.api.model.Filme;
import br.com.cinerecomenda.api.model.FilmeComNota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {
    @Query(value = "SELECT id_filme, nome, ano_lanc, nota_media FROM v_filmes_com_nota", nativeQuery = true)
    List<FilmeComNota> findFilmesComNota();
}