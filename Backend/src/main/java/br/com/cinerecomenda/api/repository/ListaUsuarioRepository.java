package br.com.cinerecomenda.api.repository;

import br.com.cinerecomenda.api.model.ListaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListaUsuarioRepository extends JpaRepository<ListaUsuario, Long> {
    List<ListaUsuario> findByUsuarioIdUsuario(Long idUsuario);
    void deleteByUsuarioIdUsuarioAndFilmeIdFilme(Long idUsuario, Long idFilme);
    @Modifying // Indica que esta query modifica dados
    @Query("UPDATE ListaUsuario lu SET lu.estado = :estado WHERE lu.usuario.idUsuario = :idUsuario AND lu.filme.idFilme = :idFilme")
    void updateEstado(@Param("idUsuario") Long idUsuario, @Param("idFilme") Long idFilme, @Param("estado") String estado);
}