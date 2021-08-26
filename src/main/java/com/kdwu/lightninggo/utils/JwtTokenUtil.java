package com.kdwu.lightninggo.utils;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.security.auth.message.AuthException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {

    // 24 * 60 * 60 * 1000 * n
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000 * 1;
    /**
     * JWT SECRET KEY
     */
    private static final String SECRET = "lightninggo123456";

    /**
     * 建立TOKEN
     */
    public String generateToken(UserDetails details) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("username", details.getUsername());
        map.put("created", new Date());

        return this.generateJwt(map);
    }

    /**
     * 建立JWT
     * @param map
     * @return
     */
   private String generateJwt(Map<String, Object> map){
         return Jwts.builder()
                 .setClaims(map)
                 .signWith(SignatureAlgorithm.HS512, SECRET)
                 .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                 .compact();
    }

    public Claims getTokenBody(String token) throws Exception {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new AuthException("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            throw new AuthException("Unsupported JWT token");
        } catch (MalformedJwtException e) {
            throw new AuthException("Invalid JWT token.");
        } catch (SignatureException e) {
            throw new AuthException("Invalid JWT signature.");
        } catch (IllegalArgumentException e) {
            throw new AuthException("JWT token compact of handler are invalid");
        }
    }

    /**
     * 根據token取得用戶名
     * @param token
     * @return
     * @throws Exception
     */
    public String getUsernameByToken(String token) throws Exception {
        return (String) this.getTokenBody(token).get("username");
    }

    /**
     * 根據token判斷當前時間內，token是否過期
     * @param token
     * @return
     * @throws Exception
     */
    public boolean isExpiration(String token) throws Exception{
        return this.getTokenBody(token).getExpiration().before(new Date());
    }

    /**
     * 刷新Token
     * @param token
     * @return
     * @throws Exception
     */
    public String refreshToken(String token) throws Exception {
        Claims claims = this.getTokenBody(token);
        claims.setExpiration(new Date());
        return this.generateJwt(claims);
    }
}
