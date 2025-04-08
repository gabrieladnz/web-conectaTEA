package conectaTEA.conectaTEA.controllers;

import conectaTEA.conectaTEA.dtos.RoomDto;
import conectaTEA.conectaTEA.dtos.RoomDtoResponse;
import conectaTEA.conectaTEA.services.rest.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomRestController {

    private final RoomService roomService;

    public RoomRestController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<RoomDtoResponse> createRoom(@RequestBody RoomDto roomDto) {
        return ResponseEntity.ok(roomService.createRoom(roomDto));
    }

    @GetMapping
    public ResponseEntity<List<RoomDtoResponse>> listRoom(){
        return ResponseEntity.status(200).body(roomService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDtoResponse> getById(@PathVariable Long id){
        return ResponseEntity.status(200).body(roomService.getById(id));
    }


}
