package murguiatech.demo.jwt.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

public class JwtService {

    @Value("${secret.jwt.key}")
    private static String SECRET_KEY;

    //User implements UserDetails
    public String getToken(UserDetails user) {
        return generateToken(new HashMap<>(), user);
    }

    private String generateToken(HashMap<String,Object> extraclaims, UserDetails user) {
        return Jwts.builder()
                .setClaims(extraclaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer("MurguiaTech")
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
