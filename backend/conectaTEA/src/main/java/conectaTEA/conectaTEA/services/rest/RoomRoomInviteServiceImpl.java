package conectaTEA.conectaTEA.services.rest;

import conectaTEA.conectaTEA.config.security.TokenService;
import conectaTEA.conectaTEA.dtos.RoomInviteDTO;
import conectaTEA.conectaTEA.dtos.RoomInviteDTOResponse;
import conectaTEA.conectaTEA.enumerators.StatusInviteEnum;
import conectaTEA.conectaTEA.models.Room;
import conectaTEA.conectaTEA.models.RoomInvite;
import conectaTEA.conectaTEA.models.RoomUsers;
import conectaTEA.conectaTEA.models.User;
import conectaTEA.conectaTEA.repositories.RoomInvitesRepository;
import conectaTEA.conectaTEA.repositories.RoomRepository;
import conectaTEA.conectaTEA.repositories.RoomUsersRepository;
import conectaTEA.conectaTEA.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomRoomInviteServiceImpl implements RoomInviteService {

    private final RoomInvitesRepository roomInvitesRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final RoomUsersRepository roomUsersRepository;
    private final TokenService tokenService;

    @Autowired
    public RoomRoomInviteServiceImpl(RoomInvitesRepository roomInvitesRepository, UserRepository userRepository, RoomRepository roomRepository, RoomUsersRepository roomUsersRepository, TokenService tokenService) {
        this.roomInvitesRepository = roomInvitesRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.roomUsersRepository = roomUsersRepository;
        this.tokenService = tokenService;
    }

    @Override
    public void create(RoomInviteDTO dto) {
        User sender = userRepository.findById(dto.senderId())
                .orElseThrow(() -> new RuntimeException("Remetente não encontrado"));
        User recipient = userRepository.findById(dto.recipientId())
                .orElseThrow(() -> new RuntimeException("Destinatário não encontrado"));
        Room room = roomRepository.findById(dto.roomId())
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));

        if (isDuplicateInvite(sender.getId(), recipient.getId(), room.getId())) {
            throw new RuntimeException("Já existe um convite pendente para esta sala e destinatário.");
        }

        RoomInvite roomInvite = new RoomInvite();
        roomInvite.setSender(sender);
        roomInvite.setRecipient(recipient);
        roomInvite.setRoom(room);
        roomInvite.setCreatedAt(LocalDateTime.now());
        roomInvite.setStatus(StatusInviteEnum.PENDING);

        roomInvitesRepository.save(roomInvite);
    }

    @Override
    public void sendInvite(RoomInviteDTO dto) {
        create(dto);
    }

    @Override
    public RoomInviteDTOResponse getById(Long id) {
        RoomInvite roomInvite = roomInvitesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Convite não encontrado"));

        return RoomInviteDTOResponse.fromEntity(roomInvite);
    }

    @Override
    public List<RoomInviteDTOResponse> getAll() {
        return roomInvitesRepository.findAll()
                .stream()
                .map(RoomInviteDTOResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Long id, RoomInviteDTO dto) {
        RoomInvite roomInvite = roomInvitesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Convite não encontrado"));

        User sender = userRepository.findById(dto.senderId())
                .orElseThrow(() -> new RuntimeException("Remetente não encontrado"));
        User recipient = userRepository.findById(dto.recipientId())
                .orElseThrow(() -> new RuntimeException("Destinatário não encontrado"));

        roomInvite.setSender(sender);
        roomInvite.setRecipient(recipient);

        roomInvitesRepository.save(roomInvite);
    }

    @Override
    public void delete(Long id) {
        roomInvitesRepository.deleteById(id);
    }

    @Override
    public void acceptInvite(Long id) {
        RoomInvite roomInvite = roomInvitesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Convite não encontrado"));

        User authenticatedUser = tokenService.getAuthenticatedUserId();

        if (!roomInvite.getRecipient().getId().equals(authenticatedUser.getId())) {
            throw new RuntimeException("Você não tem permissão para aceitar este convite.");
        }

        if (roomInvite.getStatus() != StatusInviteEnum.PENDING) {
            throw new RuntimeException("Convite já foi respondido.");
        }

        roomInvite.setStatus(StatusInviteEnum.ACCEPTED);
        roomInvitesRepository.save(roomInvite);

        RoomUsers userInRoom = RoomUsers.builder()
                .room(roomInvite.getRoom())
                .user(roomInvite.getRecipient()) //
                .createdAt(LocalDateTime.now())
                .build();

        roomUsersRepository.save(userInRoom);
    }

    @Override
    public void rejectInvite(Long id) {
        RoomInvite roomInvite = roomInvitesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Convite não encontrado"));

        User authenticatedUser = tokenService.getAuthenticatedUserId();

        if (!roomInvite.getRecipient().getId().equals(authenticatedUser.getId())) {
            throw new RuntimeException("Você não tem permissão para recusar este convite.");
        }

        if (roomInvite.getStatus() != StatusInviteEnum.PENDING) {
            throw new RuntimeException("Convite já foi respondido.");
        }

        roomInvite.setStatus(StatusInviteEnum.REJECTED);
        roomInvitesRepository.save(roomInvite);
    }

    @Override
    public List<RoomInviteDTOResponse> getReceivedInvites(Long userId) {
        User recipient = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return roomInvitesRepository.findByRecipientAndStatus(recipient, StatusInviteEnum.PENDING)
                .stream()
                .map(RoomInviteDTOResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isDuplicateInvite(Long senderId, Long receiverId, Long roomId) {
        return roomInvitesRepository
                .findBySenderIdAndRecipientIdAndRoomIdAndStatus(
                        senderId, receiverId, roomId, StatusInviteEnum.PENDING)
                .isPresent();
    }
}
