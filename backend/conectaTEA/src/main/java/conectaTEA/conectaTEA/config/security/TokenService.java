package conectaTEA.conectaTEA.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import conectaTEA.conectaTEA.dtos.TokenDTO;
import conectaTEA.conectaTEA.models.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public TokenDTO generateToken(User usuario) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return TokenDTO.builder()
                    .token(JWT.create()
                            .withIssuer("auth-api")
                            .withSubject(usuario.getId().toString())
                            .withExpiresAt(genExpirantionDate())
                            .sign(algorithm))
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

    private Instant genExpirantionDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
