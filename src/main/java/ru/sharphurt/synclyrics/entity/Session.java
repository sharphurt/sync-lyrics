package ru.sharphurt.synclyrics.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sharphurt.synclyrics.dto.spotify.SpotifyTokenResponseDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "session")
@NoArgsConstructor
public class Session {

    @Id
    private UUID sessionId;
    private String userId;
    @Column(length = 512)
    private String accessToken;
    @Column(length = 512)
    private String refreshToken;
    private LocalDateTime accessExpiresAt;
    private LocalDateTime lastTokenUpdate;
    private Boolean hasFetchedByUuid;

    public Session(String uuid, String userId, SpotifyTokenResponseDto tokenDto) {
        this.userId = userId;
        this.sessionId = UUID.fromString(uuid);
        this.accessToken = tokenDto.getAccess_token();
        this.refreshToken = tokenDto.getRefresh_token();
        this.accessExpiresAt = LocalDateTime.now().plusSeconds(tokenDto.getExpires_in());
        this.lastTokenUpdate = LocalDateTime.now();
        this.hasFetchedByUuid = false;
    }
}
