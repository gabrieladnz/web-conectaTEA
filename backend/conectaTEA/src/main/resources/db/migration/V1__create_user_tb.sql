-- V1__create_user_tb.sql

-- Verifica se a sequência já existe antes de tentar criá-la
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_class WHERE relname = 'user_tb_id_seq') THEN
CREATE SEQUENCE client.user_tb_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
END IF;
END $$;

-- Criação da tabela 'user_tb'
CREATE TABLE client.user_tb (
                                id serial NOT NULL,
                                name varchar(100) NOT NULL,
                                username varchar(200) NOT NULL,
                                password varchar(150) NOT NULL,
                                email varchar(100) NOT NULL,
                                createdAt timestamp NOT NULL,
                                updatedAt timestamp,
                                CONSTRAINT user_tb_pk PRIMARY KEY (id)
);

-- Associando a sequência à coluna 'id' para auto incremento
ALTER SEQUENCE client.user_tb_id_seq
    OWNED BY client.user_tb.id;

-- A sequência já existe e foi associada à coluna 'id', então o auto incremento vai funcionar.
