package br.com.dvsm.todolist.config.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.dvsm.todolist.model.Usuario;
import jakarta.annotation.PostConstruct;

@Service
public class JwtService {

    //@Value("${prontunet-j.secret}")
    private String secret = "1234567890";
    private final String ISSUER = "DVSM";
    private Algorithm ALGORITHM;

    @PostConstruct
    public void init() {
        this.ALGORITHM = Algorithm.HMAC256(secret);
    }

        public String generateTokenFromUsuario(Usuario usuario) {

        try {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(getExpirationDateTime())
                    .sign(ALGORITHM);
        } catch (JWTCreationException ex) {
            throw new JWTCreationException("Erro ao gerar token.", ex);
        }

    }

    public String getSubjectFromToken(String token) {

        try {
            return JWT.require(ALGORITHM)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException ex) {
            throw new JWTVerificationException("Token inválido ou expirado.", ex);
        }

    }

    private Instant getExpirationDateTime() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.ofHours(-3));
    }

}
