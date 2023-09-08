package ru.gb.authorizationservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.gb.common.constants.InfoMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements InfoMessage {
    private final PublicKey secretPublic;
    private static final String SECRET_PATH="secret/";

    public JwtTokenUtil() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] publicKeyBytes = getPublicKey();
        KeyFactory kf = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        this.secretPublic = kf.generatePublic(publicKeySpec);
    }

    @Value("${jwt.lifetime}")
    private Integer jwtLifetime;

    private Algorithm buildJwtAlgorithm(byte[] publicKeyBytes, byte[] privateKeyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey publicKey = kf.generatePublic(publicKeySpec);
        PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

        return Algorithm.RSA512((RSAPublicKey) publicKey, (RSAPrivateKey) privateKey);
    }

    public String generateToken(UserDetails userDetails, Long userId) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        Map<String, Object> claims = new HashMap<>();
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList().get(0);   //  в UserDetails только 1 роль
        claims.put(ROLE, role);                           // список ролей

        Date issuedDate = new Date();               //  время создания токена
        Date expiredDate = new Date(issuedDate.getTime() + jwtLifetime);    //  время окончания жизни токена

        return JWT.create()
                .withClaim(ROLE, role)                              //  роль пользователя
                .withSubject(String.valueOf(userId))                        //  Id пользователя
                .withIssuedAt(issuedDate)                                   //  время создания
                .withExpiresAt(expiredDate)                                 //  время окончания жизни
                .sign(buildJwtAlgorithm(getPublicKey(), getPrivateKey()));  //  подпись

    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretPublic)
                .parseClaimsJws(token)
                .getBody();

    }

    public void generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        KeyPair pair = generator.generateKeyPair();
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        try (FileOutputStream fos = new FileOutputStream(SECRET_PATH + "public.key")) {
            fos.write(publicKey.getEncoded());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileOutputStream fos = new FileOutputStream(SECRET_PATH + "private.key")) {
            fos.write(privateKey.getEncoded());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private byte[] getPrivateKey() throws IOException
    {
        File privateKeyFile = new File(SECRET_PATH + "private.key");
        return Files.readAllBytes(privateKeyFile.toPath());
    }

    private byte[] getPublicKey() throws IOException {
        File publicKeyFile = new File(SECRET_PATH + "public.key");
        return Files.readAllBytes(publicKeyFile.toPath());
    }

    public String getUserIdFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public String getRole(String token) {
        return getAllClaimsFromToken(token).get(ROLE, String.class);
    }


    public String validateJwtToken(String authToken)
    {
        Jwts.parser().setSigningKey(secretPublic).parseClaimsJws(authToken);
        return "";
    }

}
