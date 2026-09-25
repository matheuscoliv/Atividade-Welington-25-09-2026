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
            System.out.println("Atividade: \n\n");


            Fornecedor fornecedor = new Fornecedor("Distribuidora Brasil", "(62) 3333-4444");
            fornecedorDAO.inserir(fornecedor);
            System.out.println("Fornecedor inserido com ID: " + fornecedor.getIdFornecedor());


            Produto produto = new Produto("Parafuso 10mm", "SKU-001", new BigDecimal("4.50"), fornecedor.getIdFornecedor());
            produtoDAO.inserir(produto);
            System.out.println("Produto inserido: " + produto);


            var produtos = fornecedorDAO.listarProdutosPorFornecedor(fornecedor.getIdFornecedor());
            System.out.println("Produtos do fornecedor: " + produtos);


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