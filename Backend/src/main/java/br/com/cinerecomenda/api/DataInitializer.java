package br.com.cinerecomenda.api;

import br.com.cinerecomenda.api.model.Filme;
import br.com.cinerecomenda.api.model.Genero;
import br.com.cinerecomenda.api.repository.FilmeRepository;
import br.com.cinerecomenda.api.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Override
    public void run(String... args) throws Exception {

        if (filmeRepository.count() == 0) {
            System.out.println("--- BANCO DE DADOS VAZIO. INICIANDO POPULAÇÃO DE DADOS INICIAIS ---");

            // 1. Criar e salvar os Gêneros primeiro
            Genero acao = new Genero();
            acao.setNome("Ação");
            generoRepository.save(acao);

            Genero ficcao = new Genero();
            ficcao.setNome("Ficção Científica");
            generoRepository.save(ficcao);

            Genero drama = new Genero();
            drama.setNome("Drama");
            generoRepository.save(drama);

            // 2. Criar e salvar os Filmes, já associando os gêneros
            Filme filme1 = new Filme();
            filme1.setNome("Matrix");
            filme1.setAnoLanc(new Date()); // Exemplo com data atual
            filme1.setSinopse("Um hacker descobre que a realidade é uma simulação e se junta a uma rebelião.");
            filme1.setDuracao("02:16:00");
            filme1.setGeneros(Set.of(acao, ficcao)); // Associa os gêneros
            filmeRepository.save(filme1);

            Filme filme2 = new Filme();
            filme2.setNome("Clube da Luta");
            filme2.setAnoLanc(new Date());
            filme2.setSinopse("Um homem deprimido que sofre de insônia conhece um vendedor de sabão e juntos formam um clube clandestino.");
            filme2.setDuracao("02:19:00");
            filme2.setGeneros(Set.of(drama)); // Associa o gênero
            filmeRepository.save(filme2);

            System.out.println("--- DADOS INICIAIS CADASTRADOS COM SUCESSO ---");
        } else {
            System.out.println("--- BANCO DE DADOS JÁ POPULADO. NENHUMA AÇÃO NECESSÁRIA ---");
        }
    }
}