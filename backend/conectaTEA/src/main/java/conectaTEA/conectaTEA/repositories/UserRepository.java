package conectaTEA.conectaTEA.repositories;

import conectaTEA.conectaTEA.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findUserByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<UserDetails> findUserDetailsById(Long id);

}
