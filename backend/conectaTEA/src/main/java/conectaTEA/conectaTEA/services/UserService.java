package conectaTEA.conectaTEA.services;

import conectaTEA.conectaTEA.config.security.TokenService;
import conectaTEA.conectaTEA.dtos.LoginDTO;
import conectaTEA.conectaTEA.dtos.TokenDTO;
import conectaTEA.conectaTEA.dtos.UserDTO;
import conectaTEA.conectaTEA.dtos.UserDTOResponse;
import conectaTEA.conectaTEA.exceptions.BusinessException;
import conectaTEA.conectaTEA.models.User;
import conectaTEA.conectaTEA.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService{

    private final UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTOResponse create(UserDTO object) {
        try {
            Optional<User> existingUser = userRepository.findUserByUsername(object.username());
            if (existingUser.isPresent()) {
                throw new BusinessException("Usuário já existe");
            }

            Optional<User> existingEmail = userRepository.findUserByEmail(object.email());
            if (existingEmail.isPresent()) {
                throw new BusinessException("Email já cadastrado");
            }

            String passwordEncoded = new BCryptPasswordEncoder().encode(object.password());

            User user = User.builder()
                    .name(object.name())
                    .username(object.username())
                    .email(object.email())
                    .password(passwordEncoded)
                    .createdAt(LocalDateTime.now())
                    .build();
            userRepository.save(user);



            return UserDTOResponse.fromEntity(user);
        } catch (Exception e) {
            throw new BusinessException("Erro ao criar usuário");
        }
    }

    public UserDTOResponse getById(Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                return UserDTOResponse.fromEntity(user.get());
            } else {
                throw new BusinessException("Usuário não encontrado");
            }
        } catch (Exception e) {
            throw new BusinessException("Usuário não encontrado");
        }
    }

    public List<UserDTOResponse> getAll() {
        try {
            return userRepository.findAll().stream()
                    .map(UserDTOResponse::fromEntity)
                    .toList();
        } catch (Exception e) {
            throw new BusinessException("Erro ao buscar usuários");
        }
    }


    public UserDTOResponse update(Long id, UserDTO object) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                user.get().setName(object.name());
                user.get().setUsername(object.username());
                user.get().setEmail(object.email());
                userRepository.save(user.get());
                return UserDTOResponse.fromEntity(user.get());
            } else {
                throw new BusinessException("Usuário não encontrado");
            }
        } catch (Exception e) {
            throw new BusinessException("Erro ao atualizar usuário");
        }
    }


    public void delete(Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                userRepository.delete(user.get());
            } else {
                throw new BusinessException("Usuário não encontrado");
            }
        } catch (Exception e) {
            throw new BusinessException("Erro ao excluir usuário");
        }
    }

    public UserDetails findById(Long id) {
        Optional<UserDetails> user = userRepository.findUserDetailsById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new BusinessException("Usuário não encontrado");
        }
    }

    public TokenDTO login (LoginDTO object) {

        TokenDTO token = userHasToken(object.username());
        if (token != null) {
            return token;
        }
        Optional<User> userDb = userRepository.findUserByUsername(object.username());
        if (userDb.isPresent()) {
            User user = userDb.get();
            var userPassword = new UsernamePasswordAuthenticationToken(user.getUsername(), object.password());
             try{
                 Authentication authentication = authenticationManager.authenticate(userPassword);
                 token = tokenService.generateToken((User) authentication.getPrincipal());
                 tokenService.saveToken(user.getUsername(), token.token(), token.expiration().toEpochMilli());
                 return token;
             } catch (BadCredentialsException e){
                    throw new BusinessException("Usuário ou senha inválidos");
             }
        } else {
            throw new BusinessException("Usuário não encontrado");
        }
    }

    public void logout() {
        String username = tokenService.getAuthenticatedUserId().getUsername();
        tokenService.deleteToken(username);
    }

    private TokenDTO userHasToken(String username) {
        String token = tokenService.getToken(username);
        if (token != null) {
            return TokenDTO.builder()
                    .token(token)
                    .expiration(Instant.ofEpochMilli(tokenService.getTokenExpiration(username)))
                    .build();
        }
        return null;
    }

}
