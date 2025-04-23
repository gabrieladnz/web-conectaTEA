package conectaTEA.conectaTEA.services.rest;

import conectaTEA.conectaTEA.dtos.socket.ChatMessageDTO;
import conectaTEA.conectaTEA.enumerators.TypeMessage;
import conectaTEA.conectaTEA.exceptions.BusinessException;
import conectaTEA.conectaTEA.models.Message;
import conectaTEA.conectaTEA.models.Room;
import conectaTEA.conectaTEA.models.User;
import conectaTEA.conectaTEA.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private UserService userService;
    private RoomService roomService;

    public MessageService(MessageRepository messageRepository, UserService userService, RoomService roomService){
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.roomService = roomService;
    }

    public Message save(ChatMessageDTO messageDTO){
        Optional<User> sender = userService.findByUsername(messageDTO.sender());
        if(sender.isEmpty()){
            throw new BusinessException("User not found!");
        }

        Room room = roomService.findRoomById(messageDTO.roomId());

        Message message = Message.builder()
                .user(sender.get())
                .room(room)
                .createAt(LocalDateTime.now())
                .content(messageDTO.content())
                .build();

        return messageRepository.save(message);
    }

    public List<ChatMessageDTO> getMessages(Long roomId) {
        List<Message> messages = messageRepository.findAllByRoomId(roomId);
        return messages.stream().map(
                message -> new ChatMessageDTO(
                        TypeMessage.MESSAGE,
                        message.getRoom().getId(),
                        message.getUser().getUsername(),
                        message.getContent()
                )).toList();
    }
}
