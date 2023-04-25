package demo.springproject.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.springproject.security.model.User;

public interface UserRepository extends JpaRepository <User, Long> {
	Optional<User> findByEmail(String email);
}
