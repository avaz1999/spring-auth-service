package uz.avaz.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.avaz.security.entity.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
