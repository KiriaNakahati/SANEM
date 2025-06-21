package com.sanem.share.ong.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtUtils {

    private final PublicKey publicKey;
    private final PrivateKey privateKey;
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public JwtUtils() throws Exception {
        this.publicKey  = KeyLoader.loadPublicKey("src/main/resources/public_key.pem");
        this.privateKey = KeyLoader.loadPrivateKey("src/main/resources/private_key.pem");
    }

    public String generateJwt(String subject, String userId) {
        Builder tokenBuilder = JWT.create()
                .withIssuer("API Sanem")
                .withSubject(subject)
                .withExpiresAt(ExpirationDate())
                .withIssuedAt(Date.from(Instant.now()))
                .withClaim("userId", userId);
        return tokenBuilder.sign(Algorithm.RSA256((RSAPublicKey) publicKey, (RSAPrivateKey) privateKey));
    }

    public String validate(String token) {
        try {
            Algorithm alg = Algorithm.RSA256((RSAPublicKey) publicKey, null);
            JWTVerifier verifier = JWT.require(alg)
                    .withIssuer("API Sanem")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (Exception e) {
            logger.error("Falha ao validar JWT", e);
            throw new InvalidParameterException("JWT inválido: " + e.getMessage());
        }
    }

    public Boolean boolValidate(String token) {
        try {
            Algorithm alg = Algorithm.RSA256((RSAPublicKey) publicKey, null);
            JWTVerifier verifier = JWT.require(alg)
                    .withIssuer("API Sanem")
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            logger.error("Falha ao validar JWT", e);
            return false;
        }
    }

    public String retrieveToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return (header != null && header.startsWith("Bearer "))
                ? header.substring(7)
                : null;
    }

    private Instant ExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
