package conectaTEA.conectaTEA.controllers;

import conectaTEA.conectaTEA.dtos.socket.ChatMessageDTO;
import conectaTEA.conectaTEA.services.rest.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public ChatController(MessageService messageService){
        this.messageService = messageService;
    }

    @MessageMapping("/chat.sendMessage")
    public ChatMessageDTO sendMessage(ChatMessageDTO message){
        String roomId = message.roomId().toString();
        messageService.save(message);
        messagingTemplate.convertAndSend("/topic/room." + roomId, message);
        return message;
    }
}
