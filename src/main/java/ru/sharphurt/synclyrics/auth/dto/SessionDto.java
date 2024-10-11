package ru.sharphurt.synclyrics.auth.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class SessionDto {

    private UUID sessionId;
    private String userId;
    private String accessToken;
    private LocalDateTime expiresAt;

    public Boolean isExpired() {
        return this.expiresAt.isBefore(LocalDateTime.now());
    }
}
