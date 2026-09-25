CREATE TABLE IF NOT EXISTS produto (
    id_produto SERIAL PRIMARY KEY,
    nome       VARCHAR(100) NOT NULL,
    preco      NUMERIC(10,2) NOT NULL,
    estoque    INTEGER DEFAULT 0
);


CREATE TABLE IF NOT EXISTS fornecedor (
    id_fornecedor SERIAL PRIMARY KEY,
    nome          VARCHAR(100) NOT NULL,
    telefone      VARCHAR(20)
);

ALTER TABLE produto ADD COLUMN IF NOT EXISTS id_fornecedor INTEGER;

ALTER TABLE produto
    ADD CONSTRAINT fk_produto_fornecedor
    FOREIGN KEY (id_fornecedor)
    REFERENCES fornecedor (id_fornecedor)
    ON DELETE RESTRICT;
