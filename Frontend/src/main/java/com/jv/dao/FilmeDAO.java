package com.jv.dao;

import com.jv.dao.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.jv.entities.Filme;

public class FilmeDAO {

    public void adicionarFilme(Filme filme) {
        String sql = "INSERT INTO Filmes (Nome, Ano_Lanc, Sinopse, Duracao) VALUES (?, ?, ?, ?::interval)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, filme.getNome());
            stmt.setDate(2, new java.sql.Date(filme.getAnoLanc().getTime()));
            stmt.setString(3, filme.getSinopse());
            stmt.setString(4, filme.getDuracao());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("ERRO AO ADICIONAR FILME: ");
            e.printStackTrace();
        }
    }

    public List<Filme> listarTodosFilmes() {
        List<Filme> filmes = new ArrayList<>();
        String sql = "SELECT * FROM Filmes";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Filme filme = new Filme();
                filme.setIdFilme(rs.getInt("Id_Filme"));
                filme.setNome(rs.getString("Nome"));
                filme.setAnoLanc(rs.getDate("Ano_Lanc"));
                filme.setSinopse(rs.getString("Sinopse"));
                filme.setDuracao(rs.getString("Duracao"));

                filmes.add(filme);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filmes;
    }

    public void atualizarFilme(Filme filme) {
        String sql = "UPDATE Filmes SET Nome = ?, Ano_Lanc = ?, Sinopse = ?, Duracao = ?::interval WHERE Id_Filme = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, filme.getNome());
            stmt.setDate(2, new java.sql.Date(filme.getAnoLanc().getTime()));
            stmt.setString(3, filme.getSinopse());
            stmt.setString(4, filme.getDuracao());
            stmt.setInt(5, filme.getIdFilme());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("ERRO AO ATUALIZAR FILME: ");
            e.printStackTrace();
        }
    }

    public void removerFilme(int id) {
        String sql = "DELETE FROM Filmes WHERE Id_Filme = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}