package com.cine.login.login.adapters.repository;
import com.cine.login.login.domain.model.CinemaUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<CinemaUser, Long> {
    CinemaUser findByEmail(String email);
}
