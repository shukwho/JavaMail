package com.who.shuk.JavaEmail.repository;

import com.who.shuk.JavaEmail.model.Confirmation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationRepository extends JpaRepository<Confirmation, Long> {
    Confirmation findByToken(String token);
}
