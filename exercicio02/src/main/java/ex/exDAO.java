package ex;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.SQLException;

import java.util.List;

import java.util.ArrayList;

import java.sql.ResultSet;

public class exDAO {

    // Atributos para a conexão

    private static final String URL = "jdbc:postgresql://localhost:5432/nomedoseubancodedados";

    private static final String USER = "marcela_deiro";

    private static final String PASSWORD = "NovaSenhaAqui";



    // Método para obter a conexão

    public Connection getConnection() throws SQLException {

        return DriverManager.getConnection(URL, USER, PASSWORD);

    }

    

    // Método para inserir um novo livro no banco de dados

    public void inserir(Livro livro) {

        String sql = "INSERT INTO livros (titulo, autor, ano_publicacao, disponivel) VALUES (?, ?, ?, ?)";



        try (Connection conn = getConnection();

             PreparedStatement pstmt = conn.prepareStatement(sql)) {



            pstmt.setString(1, livro.getTitulo());

            pstmt.setString(2, livro.getAutor());

            pstmt.setInt(3, livro.getAno_publicacao());

            pstmt.setBoolean(4, livro.isDisponivel());



            pstmt.executeUpdate();

            System.out.println("Livro inserido com sucesso!");

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    

    public List<Livro> listar() {

        // A consulta SQL para selecionar todos os livros

        String sql = "SELECT * FROM livros";

        List<Livro> livros = new ArrayList<>();



        try (Connection conn = getConnection();

             PreparedStatement pstmt = conn.prepareStatement(sql);

             // A classe ResultSet armazena os resultados da consulta

             ResultSet rs = pstmt.executeQuery()) {



            while (rs.next()) {

                Livro livro = new Livro();

                livro.setId(rs.getInt("id"));

                livro.setTitulo(rs.getString("titulo"));

                livro.setAutor(rs.getString("autor"));

                livro.setAno_publicacao(rs.getInt("ano_publicacao"));

                livro.setDisponivel(rs.getBoolean("disponivel"));



                livros.add(livro);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return livros;

    }

    

    public void atualizar(Livro livro) {

        String sql = "UPDATE livros SET titulo = ?, autor = ?, ano_publicacao = ?, disponivel = ? WHERE id = ?";



        try (Connection conn = getConnection();

             PreparedStatement pstmt = conn.prepareStatement(sql)) {



            pstmt.setString(1, livro.getTitulo());

            pstmt.setString(2, livro.getAutor());

            pstmt.setInt(3, livro.getAno_publicacao());

            pstmt.setBoolean(4, livro.isDisponivel());

            pstmt.setInt(5, livro.getId()); // O id é usado no WHERE para encontrar o registro



            pstmt.executeUpdate();

            System.out.println("Livro atualizado com sucesso!");

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    

    public void excluir(int id) {

        String sql = "DELETE FROM livros WHERE id = ?";



        try (Connection conn = getConnection();

             PreparedStatement pstmt = conn.prepareStatement(sql)) {



            pstmt.setInt(1, id); // Define o id do livro a ser excluído



            pstmt.executeUpdate();

            System.out.println("Livro excluído com sucesso!");

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

}