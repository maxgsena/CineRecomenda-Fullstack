package br.com.cinerecomenda.api.repository;

import br.com.cinerecomenda.api.model.ListaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListaUsuarioRepository extends JpaRepository<ListaUsuario, Long> {

    List<ListaUsuario> findByUsuarioIdUsuario(Long idUsuario);
}