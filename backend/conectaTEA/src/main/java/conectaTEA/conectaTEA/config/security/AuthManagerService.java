package conectaTEA.conectaTEA.config.security;

import conectaTEA.conectaTEA.models.User;
import conectaTEA.conectaTEA.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthManagerService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthManagerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByUsername(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }




}
