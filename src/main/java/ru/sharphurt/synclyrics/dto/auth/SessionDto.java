package ru.sharphurt.synclyrics.dto.auth;

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
}
