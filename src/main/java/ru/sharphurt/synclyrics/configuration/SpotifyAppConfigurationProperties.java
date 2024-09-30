package ru.sharphurt.synclyrics.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ru.sharphurt.synclyrics")
public class SpotifyAppConfigurationProperties {
    private App app = new App();

    @Data
    public class App {
        private String clientId;
        private String redirectUrl;
    }
}
