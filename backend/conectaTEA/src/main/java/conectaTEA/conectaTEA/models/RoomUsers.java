package conectaTEA.conectaTEA.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "room_users_tb", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomUsers {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "core.room_users_tb_id_seq")
    @SequenceGenerator(name = "core.room_users_tb_id_seq", sequenceName = "core.room_users_tb_id_seq", allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "createdat", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updatedat", nullable = true)
    private LocalDateTime updatedAt;
}
