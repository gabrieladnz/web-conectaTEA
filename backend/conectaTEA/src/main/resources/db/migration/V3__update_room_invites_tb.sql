--V3__update_room_invites_tb.sql

-- Apaga a tabela antiga, se quiser recriar (ou adapte para ALTER)
DROP TABLE IF EXISTS core.room_invites_tb;

-- Criação da nova tabela seguindo o modelo atualizado
CREATE TABLE core.room_invites_tb (
                                      id SERIAL PRIMARY KEY,

                                      sender_id INT NOT NULL,
                                      recipient_id INT NOT NULL,
                                      status VARCHAR(20) NOT NULL,
                                      createdAt TIMESTAMP NOT NULL,

                                      room_id INT NOT NULL,

                                      CONSTRAINT fk_room FOREIGN KEY (room_id) REFERENCES core.room_tb(id),
                                      CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES client.user_tb(id),
                                      CONSTRAINT fk_recipient FOREIGN KEY (recipient_id) REFERENCES client.user_tb(id)
);
