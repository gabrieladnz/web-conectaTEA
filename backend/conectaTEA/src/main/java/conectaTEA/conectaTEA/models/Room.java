package conectaTEA.conectaTEA.models;

import conectaTEA.conectaTEA.enumerators.TypeRoomEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "room_tb", schema = "core")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "core.room_tb_id_seq")
    @SequenceGenerator(name = "core.room_tb_id_seq", sequenceName = "core.room_tb_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(name = "room_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeRoomEnum roomType;

    @Column
    private String category;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(name = "createdat", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<RoomUsers> users;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<RoomInvite> invites;
}
