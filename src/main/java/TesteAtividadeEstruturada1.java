import conexao.ConnectionFactory;
import dao.FornecedorDAO;
import modelo.Fornecedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class TesteAtividadeEstruturada1 {

    public static void main(String[] args) {
        FornecedorDAO dao = new FornecedorDAO();

        try {
            System.out.println("=== ATIVIDADE ESTRUTURADA 1 (Ex1) ===");

            // TODO 4.1: Inserir fornecedor e exibir o ID gerado
            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setNome("Distribuidora Goiás Tech");
            fornecedor.setTelefone("(62) 99999-0000");
            dao.inserir(fornecedor);
            System.out.println("Fornecedor inserido com ID: " + fornecedor.getIdFornecedor());

            // TODO 4.2: Inserir produto vinculado ao fornecedor
            int idProduto = inserirProduto("Teclado Mecânico", 250.00, 15, fornecedor.getIdFornecedor());
            System.out.println("Produto inserido com ID: " + idProduto
                    + " (fornecedor " + fornecedor.getIdFornecedor() + ")");

            // TODO 4.3: Listar produtos do fornecedor (INNER JOIN)
            List<String> produtos = dao.listarProdutosPorFornecedor(fornecedor.getIdFornecedor());
            System.out.println("Produtos do fornecedor " + fornecedor.getNome() + ":");
            for (String nomeProduto : produtos) {
                System.out.println(" - " + nomeProduto);
            }

            // TODO 4.4: Tentar remover o fornecedor (deve ser bloqueado)
            try {
                dao.remover(fornecedor.getIdFornecedor());
                System.out.println("ERRO: o fornecedor foi removido, a FK não bloqueou!");
            } catch (SQLException e) {
                System.out.println("SUCESSO: Exclusão bloqueada pelo PostgreSQL como esperado!");
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("Mensagem do banco: " + e.getMessage());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insere um produto usando PreparedStatement e retorna o ID gerado
    private static int inserirProduto(String nome, double preco, int estoque, int idFornecedor)
            throws SQLException {
        String sql = "INSERT INTO produto (nome, preco, estoque, id_fornecedor) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getInstancia().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, nome);
            stmt.setDouble(2, preco);
            stmt.setInt(3, estoque);
            stmt.setInt(4, idFornecedor);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }
}