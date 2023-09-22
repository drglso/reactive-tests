package edu.reactive.demo.infraestructure.entrypoints.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReactiveJwtManager implements ReactiveJwtDecoder {
    static final Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
    @Value("${spring.security.users}")
    private String users;
    private static final String ALG = "alg";
    private static final String INVALID_JWT_ERROR_MESSAGE = "Invalid JWT token";
    private static final Integer EXPIRATION_TIME = 3600000;


    @Override
    public Mono<Jwt> decode(String token) throws JwtValidationException {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + EXPIRATION_TIME);
        if (Boolean.TRUE.equals(validateToken(token))) {
            Jwt jwt = Jwt.withTokenValue(token)
                    .header(ALG, io.jsonwebtoken.SignatureAlgorithm.HS256.getValue())
                    .subject(extractUsername(token))
                    .issuedAt(now.toInstant())
                    .expiresAt(expirationDate.toInstant())
                    .build();
            return Mono.just(jwt);
        } else {
            try {
                throw new Exception(INVALID_JWT_ERROR_MESSAGE);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String generateJwtToken(String username) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + 3600000);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public Boolean hasTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    public Boolean validateToken(String token) {
        String username = extractUsername(token);
        return (getAvailableUsers().contains(username) && !hasTokenExpired(token));
    }

    public String extractUsername(String token) {

        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public List<String> getAvailableUsers() {
        return Arrays.asList(users.split(","));
    }
}