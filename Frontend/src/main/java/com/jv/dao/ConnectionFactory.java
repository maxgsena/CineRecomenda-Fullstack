package com.jv.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() {
        try {
            String url = "jdbc:postgresql://localhost:5432/CineRecomenda";
            String user = "postgres";
            String password = "1234567";

            return DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            throw new RuntimeException("Falha na conexão com o banco de dados.", e);
        }
    }

    // --- METODO DE TESTE ---
    public static void main(String[] args) {
        System.out.println("Iniciando teste de conexão...");

        try (Connection connection = ConnectionFactory.getConnection()) {
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
        } catch (Exception e) {
            System.err.println("A conexão falhou!");
            e.printStackTrace();
        }
    }
}
