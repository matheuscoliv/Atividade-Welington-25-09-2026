package org.example;

import dao.FornecedorDAO;
import dao.ProdutoDAO;
import modelo.Fornecedor;
import modelo.Produto;

import java.math.BigDecimal;
import java.sql.SQLException;

public class TesteAtividadeEstruturada {
    public static void main(String[] args) {
        FornecedorDAO fornecedorDAO = new FornecedorDAO();
        ProdutoDAO produtoDAO = new ProdutoDAO();

        try {
            System.out.println("=== ATIVIDADE ESTRUTURADA 1 (Ex1) ===");

            // TODO 4.1: inserir fornecedor
            Fornecedor fornecedor = new Fornecedor("Distribuidora ABC", "(62) 3333-4444");
            fornecedorDAO.inserir(fornecedor);
            System.out.println("Fornecedor inserido com ID: " + fornecedor.getIdFornecedor());

            // TODO 4.2: inserir produto associado a esse fornecedor
            Produto produto = new Produto("Parafuso 10mm", "SKU-001", new BigDecimal("4.50"), fornecedor.getIdFornecedor());
            produtoDAO.inserir(produto);
            System.out.println("Produto inserido: " + produto);

            // TODO 4.3: listar produtos do fornecedor
            var produtos = fornecedorDAO.listarProdutosPorFornecedor(fornecedor.getIdFornecedor());
            System.out.println("Produtos do fornecedor: " + produtos);

            // TODO 4.4: tentar remover e capturar o bloqueio do PostgreSQL
            try {
                fornecedorDAO.remover(fornecedor.getIdFornecedor());
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