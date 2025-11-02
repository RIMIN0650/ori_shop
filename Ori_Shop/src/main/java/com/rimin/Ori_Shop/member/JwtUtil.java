package com.rimin.Ori_Shop.member;

import com.rimin.Ori_Shop.member.domain.CustomUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    
    static final SecretKey key =
            Keys.hmacShaKeyFor(Decoders.BASE64.decode(
                    "jwtpassword123jwtpassword123jwtpassword123jwtpassword123jwtpassword"
            ));

    // JWT 생성
    public static String createToken(Authentication auth) {

        var user = (CustomUser) auth.getPrincipal();
        var authorities = auth.getAuthorities().stream().map(a -> a.getAuthority())
                .collect(Collectors.joining(","));

        // .claim(이름, 값)으로 JWT에 데이터 추가 가능
        // JWT에 적힌 내용들은 암호화가 되어 전달되는 것이 아님
        // 누구나 볼 수 있기 때문에 민감한 정보는 적지 않도록
        String jwt = Jwts.builder()
                .claim("username", user.getUsername())
                .claim("displayName", user.displayName)
                .claim("authorities", authorities)
                .issuedAt(new Date(System.currentTimeMillis())) // jwt 언제 발행했는지
                .expiration(new Date(System.currentTimeMillis() + 10000)) // 유효기간 10초 , ms 단위
                .signWith(key)
                .compact();
        return jwt;
    }

    // JWT 해석
    // JWT를 파라미터로 입력하면 해석하는 함수
    public static Claims extractToken(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload();
        // claims에는 JWT에 들어있던 데이터들의 정보가 있음,
        // 위에서 저장했던 유저의 이름, 아이디, 권한, 만료시간 등등
        return claims;
    }
}