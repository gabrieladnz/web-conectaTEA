package conectaTEA.conectaTEA.models;

import conectaTEA.conectaTEA.enumerators.StatusInviteEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "room_invites_tb", schema = "core")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomInvites {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "core.room_invite_tb_id_seq")
    @SequenceGenerator(name = "core.room_invite_tb_id_seq", sequenceName = "core.room_invite_tb_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_inviter_id", nullable = false)
    private User inviter;

    @ManyToOne
    @JoinColumn(name = "user_invited_id", nullable = false)
    private User invited;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusInviteEnum status;

    @Column(name = "createdat", nullable = false)
    private LocalDateTime createdAt;


}
