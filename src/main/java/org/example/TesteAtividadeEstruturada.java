package org.example;

import dao.FornecedorDAO;
import modelo.Fornecedor;
import java.sql.SQLException;

public class TesteAtividadeEstruturada {
    public static void main(String[] args) {
        FornecedorDAO dao = new FornecedorDAO();
        try {
            System.out.println("=== ATIVIDADE ESTRUTURADA 1 (Ex1) ===");

            Fornecedor fornecedor = new Fornecedor("Distribuidora ABC", "(62) 3333-4444");
            dao.inserir(fornecedor);
            System.out.println("Fornecedor inserido com ID: " + fornecedor.getIdFornecedor());

            // TODO 4.2: insira aqui um produto associado a fornecedor.getIdFornecedor()
            // usando seu ProdutoDAO já existente

            var produtos = dao.listarProdutosPorFornecedor(fornecedor.getIdFornecedor());
            System.out.println("Produtos do fornecedor: " + produtos);

            try {
                dao.remover(fornecedor.getIdFornecedor());
                System.out.println("Fornecedor removido (não deveria acontecer se houver produto vinculado).");
            } catch (SQLException e) {
                System.out.println("SUCESSO: Exclusão bloqueada pelo PostgreSQL como esperado!");
                System.out.println("Mensagem do banco: " + e.getMessage());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}