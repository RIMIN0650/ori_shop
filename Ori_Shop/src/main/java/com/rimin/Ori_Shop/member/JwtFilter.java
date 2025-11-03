package com.rimin.Ori_Shop.member;

import com.rimin.Ori_Shop.member.domain.CustomUser;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

// 요청마다 1회만 실행됨
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // request에는 유저의 온갖 정보들이 들어있음
        // ip, 브라우저 정보, os 정보, 언어 등 출력 가능

        // response 변수 사용해서 유저를 강제로 특정 페이지로 이동 가능


        // 1. 유저가 보낸 쿠키중 jwt 이름의 쿠키가 있으면 꺼내서


        Cookie[] cookies = request.getCookies();

        if(cookies == null){
            // 다음 필터 실행해주세요
            filterChain.doFilter(request, response);
            return;
        }

        // 쿠키는 여러개 전달될 수 있으므로 항상 0번째가 jwt가 아닐 수 있음
        // 정확하게 출력하기 위해선 모든 쿠키를 출력해보면서
        // 쿠키 이름이 jwt인 것을 찾아야함
        var jwtCookie = "";
        // 반복문을 돌면서 모든 쿠키 이름을 해석하고
        // 특정 쿠키 이름이 jwt 인 경우 그 내용을 변수에 저장
        for(int i = 0; i < cookies.length; i++){
            if(cookies[i].getName().equals("jwt")){
                jwtCookie = cookies[i].getValue();
            }
        }
        System.out.println(jwtCookie);


        // 2. jwt 유효기간, 위조여부 등 확인해보고
        // 알아서 유효한지 확인
        // 이상하면 에러가 남
        Claims claim;
        try {
            claim = JwtUtil.extractToken(jwtCookie);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }

        var arr = claim.get("authorities").toString().split(",");
        var authorities = Arrays.stream(arr).map(a -> new SimpleGrantedAuthority(a)).toList();


        var customUser = new CustomUser(
                claim.get("username").toString(),
                "",
                authorities
        );
        customUser.displayName = claim.get("displayName").toString();


        // 3. 문제없으면 auth 변쉥 유저정보 넣어줌
        var authToken = new UsernamePasswordAuthenticationToken(
                // 이 부분이 auth.getPrincipal()에 추가됨
                customUser,
                null,
                authorities
        );
        authToken.setDetails(new WebAuthenticationDetailsSource()
                .buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        
        //요청들어올때마다 실행할코드~~
        filterChain.doFilter(request, response);
    }


}
