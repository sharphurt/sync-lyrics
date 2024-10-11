package ru.sharphurt.synclyrics.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "session")
public class Session {

    @Id
    private UUID sessionId;
    private String userId;
    @Column(length = 512)
    private String accessToken;
    @Column(length = 512)
    private String refreshToken;
    private LocalDateTime lastTokenUpdate;
    private LocalDateTime accessExpiresAt;
}
