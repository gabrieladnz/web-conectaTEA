-- Verifica se a sequência já existe antes de tentar criá-la
DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_class WHERE relname = 'room_tb_id_seq') THEN
            CREATE SEQUENCE core.room_tb_id_seq START WITH 1 INCREMENT BY 1;
        END IF;

        IF NOT EXISTS (SELECT 1 FROM pg_class WHERE relname = 'room_users_tb_id_seq') THEN
            CREATE SEQUENCE core.room_users_tb_id_seq START WITH 1 INCREMENT BY 1;
        END IF;

        IF NOT EXISTS (SELECT 1 FROM pg_class WHERE relname = 'room_invites_tb_id_seq') THEN
            CREATE SEQUENCE core.room_invites_tb_id_seq START WITH 1 INCREMENT BY 1;
        END IF;
    END $$;

-- Criação da tabela 'room_tb'
CREATE TABLE core.room_tb (
                              id int NOT NULL DEFAULT nextval('core.room_tb_id_seq'),
                              name varchar(100) NOT NULL,
                              description varchar(2000) NOT NULL,
                              room_type varchar(20) NOT NULL,
                              owner_id int NOT NULL,
                              createdAt timestamp NOT NULL,
                              CONSTRAINT room_tb_pk PRIMARY KEY (id),
                              CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES client.user_tb(id)
);

-- Criação da tabela 'room_users_tb'
CREATE TABLE core.room_users_tb (
                                    id int NOT NULL DEFAULT nextval('core.room_users_tb_id_seq'),
                                    room_id int NOT NULL,
                                    user_id int NOT NULL,
                                    createdAt timestamp NOT NULL,
                                    updatedAt timestamp,
                                    CONSTRAINT room_users_tb_pk PRIMARY KEY (id),
                                    CONSTRAINT fk_room_id FOREIGN KEY (room_id) REFERENCES core.room_tb(id),
                                    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES client.user_tb(id)
);

-- Criação da tabela 'room_invites_tb'
CREATE TABLE core.room_invites_tb (
                                      id int NOT NULL DEFAULT nextval('core.room_invites_tb_id_seq'),
                                      room_id int NOT NULL,
                                      user_inviter_id int NOT NULL,
                                      user_invited_id int NOT NULL,
                                      status varchar(20) NOT NULL,
                                      createdAt timestamp NOT NULL,
                                      CONSTRAINT room_invites_tb_pk PRIMARY KEY (id),
                                      CONSTRAINT fk_room_id FOREIGN KEY (room_id) REFERENCES core.room_tb(id),
                                      CONSTRAINT fk_user_inviter_id FOREIGN KEY (user_inviter_id) REFERENCES client.user_tb(id),
                                      CONSTRAINT fk_user_invited_id FOREIGN KEY (user_invited_id) REFERENCES client.user_tb(id)
);

-- Associando as sequências às colunas correspondentes
ALTER SEQUENCE core.room_tb_id_seq OWNED BY core.room_tb.id;
ALTER SEQUENCE core.room_users_tb_id_seq OWNED BY core.room_users_tb.id;
ALTER SEQUENCE core.room_invites_tb_id_seq OWNED BY core.room_invites_tb.id;
