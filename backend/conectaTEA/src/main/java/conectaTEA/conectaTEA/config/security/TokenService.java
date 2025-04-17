package conectaTEA.conectaTEA.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import conectaTEA.conectaTEA.dtos.TokenDTO;
import conectaTEA.conectaTEA.models.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Observer;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private final RedisTemplate<String, String> redisTemplate;

    TokenService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public TokenDTO generateToken(User usuario) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            Instant expiration = genExpirantionDate();
            return TokenDTO.builder()
                    .token(JWT.create()
                            .withIssuer("auth-api")
                            .withSubject(usuario.getId().toString())
                            .withExpiresAt(expiration)
                            .sign(algorithm))
                    .expiration(expiration)
                    .build();
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error enquanto gerava o token" + exception);
        }
    }

    public Long validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String subject = JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
            return Long.valueOf(subject);
        } catch (JWTVerificationException exception){
            return 0L;
        }
    }

    public String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }

    public User getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof Optional) {
                Optional<User> userDetails = (Optional<User>) principal;

                // Verifica se há um usuário presente no Optional
                if (userDetails.isPresent()) {
                    // Retorna o usuário presente no Optional
                    return userDetails.get();
                }
            }
        }

        return null; // Caso o usuário não esteja autenticado ou algo falhe
    }


    private Instant genExpirantionDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }


    public void saveToken(String username, String token, long expirationTimeInSeconds) {
        redisTemplate.opsForValue().set(username, token, expirationTimeInSeconds, TimeUnit.SECONDS);
    }

    public String getToken(String username) {
        return redisTemplate.opsForValue().get(username);
    }

    public void deleteToken(String username) {
        redisTemplate.delete(username);
    }

    public Long getTokenExpiration(String username) {
        return redisTemplate.getExpire(username, TimeUnit.MILLISECONDS); // Tempo restante de expiração em milissegundos
    }
}
