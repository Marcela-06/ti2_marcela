package meuprojeto.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import meuprojeto.model.Produto;

public class ProdutoDAO {
	private Connection conexao;

    public ProdutoDAO() {
        try {
            Class.forName("org.postgresql.Driver");
            this.conexao = DriverManager.getConnection("jdbc:postgresql://localhost:5432/produto_db", "postgres", "abacaxi222");
        } catch (ClassNotFoundException e) {
            // Se o driver não for encontrado (problema no pom.xml/classpath)
            System.err.println("ERRO FATAL: Driver do PostgreSQL não encontrado.");
            throw new RuntimeException("Falha ao carregar o driver JDBC.", e);
        } catch (SQLException e) {
            // Este bloco agora LANÇA o erro no console e para a aplicação
            System.err.println("ERRO FATAL: Falha na conexão com o Banco de Dados. Verifique usuário/senha/serviço.");
            throw new RuntimeException("Erro de Conexão com o PostgreSQL.", e);
        }
    }
    // ... o restante da classe permanece o mesmo
    public void inserir(Produto produto) throws SQLException {
        String sql = "INSERT INTO produto (nome, descricao, preco) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.executeUpdate();
        }
    }

    public List<Produto> listar() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto ORDER BY id";
        try (PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));
                produtos.add(produto);
            }
        }
        return produtos;
    }

    public void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
