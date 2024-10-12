package ru.sharphurt.synclyrics.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sharphurt.synclyrics.entity.Session;

import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {

    Optional<Session> findByUserId(String userId);

    Optional<Session> findBySessionIdAndHasFetchedByUuidIsFalse(UUID uuid);
}
