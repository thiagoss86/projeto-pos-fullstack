package com.acme.cars.service;

import com.acme.cars.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {
    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.issuer:ACME.COM}")
    private String issuer;

    @Value("${app.jwt.expiration-seconds:604800}")
    private long expirationSeconds;
    public String generateToken(Usuario usuario) {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        String jti = java.util.UUID.randomUUID().toString();
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(usuario.getId().toString())
                .withJWTId(jti)
                .withExpiresAt(new Date(System.currentTimeMillis() + (expirationSeconds * 1000)))
                .withIssuedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                .withClaim("email", usuario.getEmail())
                .sign(algorithm);
    }
    public DecodedJWT isValid(String token) {
        DecodedJWT decodedJWT;
        String valid = stripBearer(token);
        Algorithm algorithm = Algorithm.HMAC512(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();
        decodedJWT = verifier.verify(valid);
        return decodedJWT;
    }
    public String getUsuario(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }

    public String getJti(DecodedJWT decodedJWT) {
        return decodedJWT.getId();
    }

    public Date getExpiresAt(DecodedJWT decodedJWT) {
        return decodedJWT.getExpiresAt();
    }

    /** Retorna o token sem o prefixo Bearer */
    public String stripBearer(String token){
        if(token == null || !token.startsWith("Bearer")){
            throw new IllegalArgumentException("Invalid Token") ;
        }
        return token.replace("Bearer", "").trim();
    }
}
