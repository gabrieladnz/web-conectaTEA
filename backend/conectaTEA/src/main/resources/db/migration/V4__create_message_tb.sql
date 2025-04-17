-- V4__create_message_tb.sql

-- Verifica se a sequência já existe antes de tentar criá-la
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_class WHERE relname = 'message_tb_id_seq') THEN
CREATE SEQUENCE core.message_tb_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
END IF;
END $$;

-- Criação da tabela 'message_tb'
CREATE TABLE core.message_tb (
                                id serial NOT NULL,
                                room_id int NOT NULL,
                                user_id int NOT NULL,
                                content varchar(2000) NOT NULL,
                                createdAt timestamp NOT NULL,
                                CONSTRAINT fk_room_id FOREIGN KEY (room_id) REFERENCES core.room_tb(id),
                                CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES client.user_tb(id),
                                CONSTRAINT user_tb_pk PRIMARY KEY (id)
);

-- Associando a sequência à coluna 'id' para auto incremento
ALTER SEQUENCE core.message_tb_id_seq
    OWNED BY core.message_tb.id;

-- A sequência já existe e foi associada à coluna 'id', então o auto incremento vai funcionar.
