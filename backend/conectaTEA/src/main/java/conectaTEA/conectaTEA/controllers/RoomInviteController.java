package conectaTEA.conectaTEA.controllers;

import conectaTEA.conectaTEA.dtos.RoomInviteDTO;
import conectaTEA.conectaTEA.dtos.RoomInviteDTOResponse;
import conectaTEA.conectaTEA.services.rest.RoomInviteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invites")
public class RoomInviteController {

    private final RoomInviteService roomInviteService;

    public RoomInviteController(RoomInviteService roomInviteService) {
        this.roomInviteService = roomInviteService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendInvite(@RequestBody @Valid RoomInviteDTO roomInviteDTO) {
        try {
            roomInviteService.sendInvite(roomInviteDTO);
            return ResponseEntity.ok("Convite enviado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao enviar convite: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            RoomInviteDTOResponse response = roomInviteService.getById(id);
            return ResponseEntity.ok(response);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar convite: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<RoomInviteDTOResponse> responses = roomInviteService.getAll();
            return ResponseEntity.ok(responses);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar todos os convites: " + e.getMessage());
        }
    }

    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptInvite(@PathVariable Long id) {
        try {
            roomInviteService.acceptInvite(id);
            return ResponseEntity.ok("Convite aceito com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao aceitar convite: " + e.getMessage());
        }
    }

    @PostMapping("/decline/{id}")
    public ResponseEntity<?> declineInvite(@PathVariable Long id) {
        try {
            roomInviteService.rejectInvite(id);
            return ResponseEntity.ok("Convite recusado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao recusar convite: " + e.getMessage());
        }
    }

    @DeleteMapping("/revoke/{id}")
    public ResponseEntity<?> revokeInvite(@PathVariable Long id) {
        try {
            roomInviteService.delete(id);
            return ResponseEntity.ok("Convite revogado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao revogar convite: " + e.getMessage());
        }
    }

    @GetMapping("/received/{userId}")
    public ResponseEntity<?> getReceivedInvites(@PathVariable Long userId) {
        try {
            List<RoomInviteDTOResponse> invites = roomInviteService.getReceivedInvites(userId);
            return ResponseEntity.ok(invites);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao listar convites: " + e.getMessage());
        }
    }

}
