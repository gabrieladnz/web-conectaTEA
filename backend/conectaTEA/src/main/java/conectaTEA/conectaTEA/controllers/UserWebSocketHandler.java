package conectaTEA.conectaTEA.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import conectaTEA.conectaTEA.dtos.*;
import conectaTEA.conectaTEA.services.BaseService;
import conectaTEA.conectaTEA.services.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

@Component
public class UserWebSocketHandler extends TextWebSocketHandler {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UserWebSocketHandler(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        // Parse a mensagem JSON recebida
//        WebSocketRequest request = objectMapper.readValue(message.getPayload(), WebSocketRequest.class);
//
//        // Processar as operações CRUD com base no tipo de operação
//        switch (request.getAction()) {
//            case "CREATE":
//                UserDTO newUser = objectMapper.convertValue(request.getData(), UserDTO.class);
//                userService.create(newUser);
//                session.sendMessage(new TextMessage("Usuário criado com sucesso: " + objectMapper.writeValueAsString(newUser)));
//                break;
//
//            case "READ-ONE":
//                UserDTOResponse user = userService.getById(Long.parseLong(request.getData().get("id").toString()));
//                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(user)));
//                break;
//
//            case "UPDATE":
//                UpdateRequestDTO updateUser = objectMapper.convertValue(request.getData(), UpdateRequestDTO.class);
//                UserDTO userDto = UserDTO.builder()
//                        .name(updateUser.name())
//                        .email(updateUser.email())
//                        .username(updateUser.username())
//                        .build();
//                userService.update(updateUser.id(), userDto);
//                session.sendMessage(new TextMessage("Usuário atualizado com sucesso: " + objectMapper.writeValueAsString(updateUser)));
//                break;
//
//            case "DELETE":
//                Long userId = Long.parseLong(request.getData().get("id").toString());
//                userService.delete(userId);
//                session.sendMessage(new TextMessage("Usuário excluído com sucesso: " + userId));
//                break;
//
//            case "LIST":
//                List<UserDTOResponse> users = userService.getAll();
//                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(users)));
//                break;
//            case "LOGIN":
//                LoginDTO userLogin = objectMapper.convertValue(request.getData(), LoginDTO.class);
//                TokenDTO token = userService.login(userLogin);
//                session.sendMessage(new TextMessage("Usuário logado com sucesso: " + objectMapper.writeValueAsString(token)));
//                break;
//
//            default:
//                session.sendMessage(new TextMessage("Ação desconhecida"));
//                break;
//        }
    }
}
