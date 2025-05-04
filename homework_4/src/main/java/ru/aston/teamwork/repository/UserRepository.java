package ru.aston.teamwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aston.teamwork.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
