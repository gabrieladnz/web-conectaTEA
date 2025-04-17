package conectaTEA.conectaTEA.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room_invites_tb", schema = "core")
public class RoomInvite extends Invite {
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;


}
