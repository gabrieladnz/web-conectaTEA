package conectaTEA.conectaTEA.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "message_tb", schema = "core")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "core.message_tb_id_seq")
    @SequenceGenerator(name = "core.message_tb_id_seq", sequenceName = "core.message_tb_id_seq", allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false)
    private String content;
    @Column(name = "createdat", nullable = false)
    private LocalDateTime createAt;

}
