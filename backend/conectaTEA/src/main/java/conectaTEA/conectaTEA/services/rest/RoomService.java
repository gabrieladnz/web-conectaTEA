package conectaTEA.conectaTEA.services.rest;

import conectaTEA.conectaTEA.config.security.TokenService;
import conectaTEA.conectaTEA.dtos.RoomDto;
import conectaTEA.conectaTEA.dtos.RoomDtoResponse;
import conectaTEA.conectaTEA.dtos.UserDTOResponse;
import conectaTEA.conectaTEA.exceptions.BusinessException;
import conectaTEA.conectaTEA.models.Room;
import conectaTEA.conectaTEA.models.RoomUsers;
import conectaTEA.conectaTEA.models.User;
import conectaTEA.conectaTEA.repositories.RoomRepository;
import conectaTEA.conectaTEA.services.websocket.WebSocketService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    private final TokenService tokenService;

    private final UserService userService;

    private final WebSocketService webSocketService; // Injetando o WebSocketService

    public RoomService(RoomRepository roomRepository, TokenService tokenService, UserService userService, WebSocketService webSocketService) {
        this.roomRepository = roomRepository;
        this.tokenService = tokenService;
        this.userService = userService;
        this.webSocketService = webSocketService;
    }
    @Transactional
    public RoomDtoResponse createRoom (RoomDto roomDto){
        try{
            User userAuthenticated = tokenService.getAuthenticatedUserId();
            List<RoomUsers> userList = new ArrayList<>();

            Room room = Room.builder()
                    .name(roomDto.name())
                    .description(roomDto.description())
                    .roomType(roomDto.roomType())
                    .createdAt(LocalDateTime.now())
                    .owner(userAuthenticated)
                    .build();

            roomDto.usernames().forEach(
                    username -> {
                        User user = userService.findByUsername(username)
                                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));
                        if (user.getId().equals(userAuthenticated.getId())){
                            throw new BusinessException("Usuário não pode ser o dono da sala");
                        }
                        userList.add(RoomUsers.builder()
                                .user(user)
                                .createdAt(LocalDateTime.now())
                                .room(room)
                                .build());
                    }
            );
            room.setUsers(userList);

            roomRepository.save(room);


            // Criando o objeto de resposta para enviar via WebSocket
            RoomDtoResponse response = new RoomDtoResponse(
                    room.getId(),
                    room.getName(),
                    room.getDescription(),
                    room.getRoomType(),
                    room.getUsers().stream().map(RoomUsers::getUser).map(UserDTOResponse::fromEntity).toList()
            );

            webSocketService.notifyNewRoom(response);

            return response;

        } catch (Exception e){
            throw new BusinessException("Erro ao criar sala");
        }
    }

    public List<RoomDtoResponse> listAll(){
        List<Room> rooms = roomRepository.findAll();

        return rooms.stream().map(room -> {
            return new RoomDtoResponse(
                    room.getId(),
                    room.getName(),
                    room.getDescription(),
                    room.getRoomType(),
                    room.getUsers().stream().map(RoomUsers::getUser).map(UserDTOResponse::fromEntity).toList()
            );
        }).toList();
    }

    public RoomDtoResponse getById(Long id) {
        try{
            Optional<Room> roomExists = roomRepository.findById(id);
            if(!roomExists.isEmpty()){
                Room room = roomExists.get();
                return new RoomDtoResponse(
                        room.getId(),
                        room.getName(),
                        room.getDescription(),
                        room.getRoomType(),
                        room.getUsers().stream().map(RoomUsers::getUser).map(UserDTOResponse::fromEntity).toList()
                );
            }
        } catch (BusinessException e){
            e.printStackTrace();
        }
        throw new BusinessException("User not found");
    }

}
