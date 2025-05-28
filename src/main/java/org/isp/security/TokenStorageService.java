package org.isp.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Service for managing JWT tokens using Redis.
 * This service provides functionality for:
 * 1. Storing active tokens
 * 2. Blacklisting revoked tokens
 * 3. Checking if a token is valid (not blacklisted)
 */
@Service
public class TokenStorageService {
    private static final Logger logger = LoggerFactory.getLogger(TokenStorageService.class);
    private static final String TOKEN_BLACKLIST_PREFIX = "token:blacklist:";
    private static final String TOKEN_ACTIVE_PREFIX = "token:active:";

    @Value("${jwt.expiration:86400000}")
    private int jwtExpirationMs;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * Stores an active token in Redis with the username as part of the key.
     * This allows for retrieving all active tokens for a specific user.
     *
     * @param username the username associated with the token
     * @param token the JWT token
     */
    public void storeToken(String username, String token) {
        String key = TOKEN_ACTIVE_PREFIX + username + ":" + token;
        redisTemplate.opsForValue().set(key, "active", jwtExpirationMs, TimeUnit.MILLISECONDS);
        logger.info("Stored active token for user: {}", username);
    }

    /**
     * Blacklists a token by storing it in Redis.
     * Blacklisted tokens are considered invalid even if they haven't expired yet.
     *
     * @param token the JWT token to blacklist
     */
    public void blacklistToken(String token) {
        String key = TOKEN_BLACKLIST_PREFIX + token;
        redisTemplate.opsForValue().set(key, "blacklisted", jwtExpirationMs, TimeUnit.MILLISECONDS);
        logger.info("Blacklisted token");
    }

    /**
     * Checks if a token is blacklisted.
     *
     * @param token the JWT token to check
     * @return true if the token is blacklisted, false otherwise
     */
    public boolean isTokenBlacklisted(String token) {
        String key = TOKEN_BLACKLIST_PREFIX + token;
        return redisTemplate.hasKey(key);
    }

    /**
     * Invalidates all tokens for a specific user by blacklisting them.
     * This is useful for scenarios like password change or account compromise.
     *
     * @param username the username whose tokens should be invalidated
     */
    public void invalidateAllUserTokens(String username) {
        String pattern = TOKEN_ACTIVE_PREFIX + username + ":*";
        redisTemplate.keys(pattern).forEach(key -> {
            String token = key.substring((TOKEN_ACTIVE_PREFIX + username + ":").length());
            blacklistToken(token);
            redisTemplate.delete(key);
        });
        logger.info("Invalidated all tokens for user: {}", username);
    }
}
