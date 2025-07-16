package br.com.cinerecomenda.api.model;

import java.util.Date;

// Esta interface define a "forma" dos dados que virão da nossa View 'v_filmes_com_nota'.
// O Spring Data JPA irá mapear as colunas do resultado da query para estes métodos.
// É importante que o nome do método (ex: getNome) corresponda ao nome da coluna na View (nome).
public interface FilmeComNota {
    Long getId_filme();
    String getNome();
    Date getAno_lanc();
    Double getNota_media();
}