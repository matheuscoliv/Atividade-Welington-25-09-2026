package modelo;

import java.math.BigDecimal;

public class Produto {
    private int idProduto;
    private String nome;
    private String sku;
    private BigDecimal preco;
    private int idFornecedor;

    public Produto() {}

    public Produto(String nome, String sku, BigDecimal preco, int idFornecedor) {
        this.nome = nome;
        this.sku = sku;
        this.preco = preco;
        this.idFornecedor = idFornecedor;
    }

    public int getIdProduto() { return idProduto; }
    public void setIdProduto(int idProduto) { this.idProduto = idProduto; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public int getIdFornecedor() { return idFornecedor; }
    public void setIdFornecedor(int idFornecedor) { this.idFornecedor = idFornecedor; }

    @Override
    public String toString() {
        return "Produto{id=" + idProduto + ", nome='" + nome + "', sku='" + sku +
                "', preco=" + preco + ", idFornecedor=" + idFornecedor + "}";
    }
}