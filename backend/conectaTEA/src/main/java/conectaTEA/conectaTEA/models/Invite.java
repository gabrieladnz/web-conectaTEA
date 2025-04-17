package conectaTEA.conectaTEA.models;

import conectaTEA.conectaTEA.enumerators.StatusInviteEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @Enumerated(EnumType.STRING)
    private StatusInviteEnum status;

    @Column(name = "createdat", nullable = false)
    private LocalDateTime createdAt;
}
