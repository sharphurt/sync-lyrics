package ru.sharphurt.synclyrics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SynclyricsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SynclyricsApplication.class, args);
    }
}
