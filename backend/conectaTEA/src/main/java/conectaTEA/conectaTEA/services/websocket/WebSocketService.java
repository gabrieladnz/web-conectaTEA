package conectaTEA.conectaTEA.services.websocket;

import conectaTEA.conectaTEA.dtos.RoomDtoResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyNewRoom(RoomDtoResponse room) {
        // Envia a nova sala para o tópico "/topic/rooms"
        messagingTemplate.convertAndSend("/topic/rooms", room);
    }
}
