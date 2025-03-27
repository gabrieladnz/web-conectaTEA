package conectaTEA.conectaTEA.controllers;

import conectaTEA.conectaTEA.dtos.*;
import conectaTEA.conectaTEA.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/save")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO) {
        try{
            UserDTOResponse response = userService.create(userDTO);
           return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao criar usuário");
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid UserDTO updateDTO) {
        try {
            UserDTOResponse response = userService.update(id, updateDTO);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar usuário");
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário deletado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao deletar usuário");
        }
    }
    @GetMapping("{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        try {
            UserDTOResponse response = userService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar usuário");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO) {
        try {
            TokenDTO token = userService.login(loginDTO);
            return ResponseEntity.status(HttpStatus.OK).body(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao logar");
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        try {
            userService.logout();
            return ResponseEntity.status(HttpStatus.OK).body("Logout realizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao realizar logout");
        }
    }
}
