package conectaTEA.conectaTEA.config.security;

import conectaTEA.conectaTEA.repositories.UserRepository;
import conectaTEA.conectaTEA.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        if(shouldSkipFilter(request)){
            filterChain.doFilter(request, response);
            return;
        }

        String token = this.tokenService.recoverToken(request);

        try {
            if (token != null) {
                Long id = tokenService.validateToken(token);
                Optional<UserDetails> user = userRepository.findUserDetailsById(id);
                if(user.isEmpty()){
                    throw new Exception("User not found");
                }
                if(tokenService.getToken(user.get().getUsername()).isEmpty()){
                    throw new Exception("Token not found");
                }
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.get().getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

        filterChain.doFilter(request, response);
    }

    private boolean shouldSkipFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod();

        return uri.startsWith("/usuario/swagger-ui/index.html") || method.equalsIgnoreCase("OPTIONS");
    }
}
