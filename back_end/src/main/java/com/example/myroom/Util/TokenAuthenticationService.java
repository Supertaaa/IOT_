package com.example.myroom.Util;

/**
 * Created by nhs3108 on 29/03/2017.
 */

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security
        .authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static java.util.Collections.emptyList;

public class TokenAuthenticationService {


    @Value("${JWT.EXPIRATIONTIME}")
    private long EXPIRATIONTIME_; // 10 days

    @Value("${JWT.SECRET}")
    private String SECRET_;

    @Value("${JWT.TOKEN_PREFIX}")
    private String TOKEN_PREFIX_;

    @Value("${JWT.HEADER_STRING}")
    private String HEADER_STRING_;

    private static long EXPIRATIONTIME; // 10 days
    private static String SECRET;
    private static String TOKEN_PREFIX;
    private static String HEADER_STRING;

    @PostConstruct
    public void init() {
        EXPIRATIONTIME = EXPIRATIONTIME_;
        SECRET = SECRET_;
        TOKEN_PREFIX = TOKEN_PREFIX_;
        HEADER_STRING = HEADER_STRING_;
    }

    public static String getToken(String username){

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static Authentication getAuthentication(HttpServletRequest request) throws Exception {
        String token = request.getHeader(HEADER_STRING);
        String emanu = request.getHeader("Emanu");

        if (token != null && emanu != null) {
            // parse the token.
            String user = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
            if(AES.decrypt(emanu, "KEMIATHOIKEMIATH").equals(user) ){
                return new UsernamePasswordAuthenticationToken(user, null, emptyList());
            }
            throw new Exception();
        }
        throw new Exception();
    }
}