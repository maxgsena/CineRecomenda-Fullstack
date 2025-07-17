package br.com.cinerecomenda.api;

import br.com.cinerecomenda.api.model.Filme;
import br.com.cinerecomenda.api.model.Genero;
import br.com.cinerecomenda.api.model.Usuario; // Importe a entidade Usuario
import br.com.cinerecomenda.api.repository.FilmeRepository;
import br.com.cinerecomenda.api.repository.GeneroRepository;
import br.com.cinerecomenda.api.repository.UsuarioRepository; // Importe o repositório de Usuario
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

    // --- NOVA DEPENDÊNCIA ---
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {

        // --- NOVA LÓGICA PARA CRIAR O ADMIN ---
        // Verifica se o usuário admin já existe pelo email
        if (usuarioRepository.findByEmail("admin@cinerecomenda.com").isEmpty()) {
            System.out.println("--- CRIANDO USUÁRIO ADMIN PADRÃO ---");
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setEmail("admin@cinerecomenda.com");
            // Em um app real, esta senha seria criptografada (hashed)
            admin.setSenha("admin123");
            admin.setRole("ADMIN");
            usuarioRepository.save(admin);
            System.out.println("--- USUÁRIO ADMIN CRIADO ---");
        }

        // A lógica para popular filmes se a tabela estiver vazia continua a mesma
        if (filmeRepository.count() == 0) {
            System.out.println("--- BANCO DE DADOS VAZIO. POPULANDO DADOS INICIAIS DE FILMES E GÊNEROS ---");

            Genero acao = new Genero();
            acao.setNome("Ação");
            generoRepository.save(acao);

            Genero ficcao = new Genero();
            ficcao.setNome("Ficção Científica");
            generoRepository.save(ficcao);

            Filme filme1 = new Filme();
            filme1.setNome("Matrix");
            filme1.setAnoLanc(new Date());
            filme1.setSinopse("Um hacker descobre que a realidade é uma simulação.");
            filme1.setDuracao("02:16:00");
            filme1.setGeneros(Set.of(acao, ficcao));
            filmeRepository.save(filme1);

            System.out.println("--- DADOS INICIAIS CADASTRADOS COM SUCESSO ---");
        } else {
            System.out.println("--- BANCO DE DADOS JÁ POPULADO. NENHUMA AÇÃO NECESSÁRIA ---");
        }
    }
}