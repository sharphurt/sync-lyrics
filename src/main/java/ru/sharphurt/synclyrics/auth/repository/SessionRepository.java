package ru.sharphurt.synclyrics.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sharphurt.synclyrics.auth.entity.Session;

import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {

    Optional<Session> findByUserId(String userId);
}
