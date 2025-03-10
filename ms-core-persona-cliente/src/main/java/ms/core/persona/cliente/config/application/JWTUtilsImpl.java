package ms.core.persona.cliente.config.application;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class JWTUtilsImpl {
    @Value("${security.jwt.key.private}")
    private String jwtPrivateKey;
    @Value("${security.jwt.claim.key.private}")
    private String jwtClaimKey;
    @Value("${security.jwt.user.generator}")
    private String jwtUserGenerator;
    @Value("${security.jwt.expiration}")
    private Long JWT_EXPIRATION;

    private String BASE_CLAIM_KEY = "identificacion";


    public String generateToken(Authentication authentication) {
        String encryptedSubject = encrypt(authentication.getName()); // Encriptamos el username
        return createToken(encryptedSubject);
    }

    // Crea el token con el subject encriptado
    private String createToken(String encryptedSubject) {
        Algorithm algorithm = Algorithm.HMAC256(jwtPrivateKey);
        return JWT.create()
                .withSubject(encryptedSubject)
                .withIssuer(jwtUserGenerator)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date())
                .sign(algorithm);
    }

    // Extrae el subject encriptado y lo desencripta para obtener el username
    public String extractUsername(DecodedJWT token) {
        return decrypt(token.getSubject()); // Desencriptamos para obtener el username original
    }

    public Date extractExpiration(DecodedJWT token) {
        return token.getExpiresAt();
    }

    // Extrae un claim específico del token
    public Claim getSpecificClaim(DecodedJWT decodedToken, String claim){
        return decodedToken.getClaim(claim);
    }

    public Map<String, Claim> getAllClaims(DecodedJWT decodedToken){
        return decodedToken.getClaims();
    }

    // Valida el token contra el UserDetails
    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtPrivateKey);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(jwtUserGenerator)
                    .build();

            return verifier.verify(token);
        } catch (JWTVerificationException e){
            // desactivo el token que en base esta activo

            throw new RuntimeException("Token no válido", e);
        }
    }

    // Encripta el subject (username)
    private String encrypt(String data) {
        try {
            SecretKeySpec key = new SecretKeySpec(jwtClaimKey.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Error al encriptar el subject", e);
        }
    }

    // Desencripta el subject
    private String decrypt(String encryptedData) {
        try {
            SecretKeySpec key = new SecretKeySpec(jwtClaimKey.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error al desencriptar el subject", e);
        }
    }
}
