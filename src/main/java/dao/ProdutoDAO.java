package dao;

import modelo.Produto;
import conexao.ConnectionFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoDAO {

    private Connection getConnection() throws SQLException {
        return ConnectionFactory.getInstancia().getConnection();
    }

    private Produto mapearProduto(ResultSet rs) throws SQLException {
        Produto produto = new Produto();
        produto.setIdProduto(rs.getInt("id_produto"));
        produto.setNome(rs.getString("nome"));
        produto.setSku(rs.getString("sku"));
        produto.setPreco(rs.getBigDecimal("preco"));
        produto.setIdFornecedor(rs.getInt("id_fornecedor"));
        return produto;
    }

    public void inserir(Produto produto) throws SQLException {
        String sql = "INSERT INTO produto (nome, sku, preco, id_fornecedor) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getSku());
            stmt.setBigDecimal(3, produto.getPreco());
            stmt.setInt(4, produto.getIdFornecedor());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    produto.setIdProduto(rs.getInt(1));
                }
            }
        }
    }

    public Optional<Produto> buscarPorSku(String sku) throws SQLException {
        String sql = "SELECT id_produto, nome, sku, preco, id_fornecedor FROM produto WHERE sku = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sku);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearProduto(rs));
                }
                return Optional.empty();
            }
        }
    }

    public List<Produto> listarTodos() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT id_produto, nome, sku, preco, id_fornecedor FROM produto";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                produtos.add(mapearProduto(rs));
            }
        }
        return produtos;
    }
}