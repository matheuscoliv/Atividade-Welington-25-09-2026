
-- Atividade Estruturada 1 (Ex1) - Etapa 1: Modelagem DDL


-- Pré-requisito: tabela produto
CREATE TABLE IF NOT EXISTS produto (
    id_produto SERIAL PRIMARY KEY,
    nome       VARCHAR(100) NOT NULL,
    preco      NUMERIC(10,2) NOT NULL,
    estoque    INTEGER DEFAULT 0
);

-- TODO 1.1: Tabela fornecedor
CREATE TABLE IF NOT EXISTS fornecedor (
    id_fornecedor SERIAL PRIMARY KEY,
    nome          VARCHAR(100) NOT NULL,
    telefone      VARCHAR(20)
);

-- TODO 1.2: Coluna id_fornecedor em produto + chave estrangeira
-- A coluna aceita NULL porque produtos já cadastrados
-- podem não possuir fornecedor associado.
ALTER TABLE produto ADD COLUMN IF NOT EXISTS id_fornecedor INTEGER;

ALTER TABLE produto
    ADD CONSTRAINT fk_produto_fornecedor
    FOREIGN KEY (id_fornecedor)
    REFERENCES fornecedor (id_fornecedor)
    ON DELETE RESTRICT;

-- TODO 1.3: Justificativa técnica do ON DELETE RESTRICT
-- Optamos por ON DELETE RESTRICT em vez de CASCADE porque um produto
-- é um dado de negócio relevante (histórico de vendas, estoque, preço)
-- que não deve desaparecer como efeito colateral da exclusão de um
-- fornecedor. Com CASCADE, remover um fornecedor apagaria
-- silenciosamente todos os seus produtos, podendo causar perda de dados
-- e inconsistência nas vendas já registradas. Com RESTRICT, o próprio
-- PostgreSQL garante a integridade referencial: a exclusão só é
-- permitida depois que os produtos forem desvinculados ou transferidos
-- para outro fornecedor, o que obriga a uma decisão consciente.