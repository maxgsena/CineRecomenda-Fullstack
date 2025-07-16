package com.jv.entities;

import java.util.Date;

public class Filme {

    private int idFilme;
    private String nome;
    private Date anoLanc;
    private String sinopse;
    private String duracao;

    public Filme() {
    }


    public int getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(int idFilme) {
        this.idFilme = idFilme;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getAnoLanc() {
        return anoLanc;
    }

    public void setAnoLanc(Date anoLanc) {
        this.anoLanc = anoLanc;
    }


    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    @Override
    public String toString() {
        return "Filme [ID=" + idFilme + ", Nome=" + nome + ", Gênero=" + "]";
    }
}