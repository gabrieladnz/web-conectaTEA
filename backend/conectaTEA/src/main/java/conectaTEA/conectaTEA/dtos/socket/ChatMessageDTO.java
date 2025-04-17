package conectaTEA.conectaTEA.dtos.socket;

import conectaTEA.conectaTEA.enumerators.TypeMessage;
import conectaTEA.conectaTEA.models.Message;
import jakarta.validation.constraints.NotNull;

public record ChatMessageDTO(
       @NotNull(message = "Please provide typeMessage") TypeMessage type,
       @NotNull(message = "Please provide roomId") Long roomId,
       @NotNull(message = "Please provide sender") String sender,
       @NotNull(message = "Please provide content") String content
) {
}
