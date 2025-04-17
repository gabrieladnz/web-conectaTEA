package conectaTEA.conectaTEA.controllers;

import conectaTEA.conectaTEA.dtos.socket.ChatMessageDTO;
import conectaTEA.conectaTEA.services.rest.MessageService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private MessageService messageService;

    public ChatController(MessageService messageService){
        this.messageService = messageService;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/room.{roomId}")
    public ChatMessageDTO sendMessage(@DestinationVariable String roomId, ChatMessageDTO message){
        messageService.save(message);
        return message;
    }
}
