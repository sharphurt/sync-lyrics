package ru.sharphurt.synclyrics.pkceauth.util;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
public class CryptographicUtil {

    public static String generateCodeChallenge(String codeVerifier) {
        byte[] digest = null;
        try {
            var bytes = codeVerifier.getBytes(StandardCharsets.US_ASCII);
            var messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bytes, 0, bytes.length);
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException exception) {
            log.error("Unable to generate code challenge {0}", exception);
        }

        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }
}
