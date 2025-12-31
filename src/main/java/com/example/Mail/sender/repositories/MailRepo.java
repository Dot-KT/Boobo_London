package com.example.Mail.sender.repositories;

import com.example.Mail.sender.models.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailRepo extends JpaRepository<Guest, Integer> {
    Optional<Guest> findByEmail(String email);
}
