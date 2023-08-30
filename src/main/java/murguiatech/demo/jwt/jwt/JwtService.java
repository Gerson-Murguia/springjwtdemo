package murguiatech.demo.jwt.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import murguiatech.demo.jwt.config.EnviromentConfig;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final EnviromentConfig enviromentConfig;

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
        byte[] keyBytes= Decoders.BASE64.decode(enviromentConfig.getSECRET_KEY());
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String getUsernameFromToken(String token) {

        return getClaims(token,Claims::getSubject);
    }

    private Date getExpirationDateFromToken(String token){
        return getClaims(token,Claims::getExpiration);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaims(String token, Function<Claims,T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired(String token){
        return getExpirationDateFromToken(token).before(new Date());
    }

}
