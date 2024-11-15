package edu.miu.cs489.securitydemo.user.repository;

import edu.miu.cs489.securitydemo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
