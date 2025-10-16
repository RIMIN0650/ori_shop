package com.rimin.Ori_Shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// spring security 의 설정을 만질 수 있음
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // spring이 이 코드를 가져가서 bean으로 만들어준다
    }

    // Bean?
    // spring이 뽑은 object
    // bean으로 만들고싶은 object가 있다면 그 object를 뱉는 함수를 하나 만들고
    // bean, configuration 하면 spring이 자동으로 bean으로 만들어준다.
    // 아무데서나 등록 후 dependency injection으로 사용 가능





    @Bean
    // 어떤 페이지를 로그인 검사할지 설정 가능
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // FilterChain : 모든 유저의 요청과 서버의 응답 사이에
        // 자동으로 실행해주고 싶은 코드 담는 곳
        http.csrf((csrf)-> csrf.disable());
        http.authorizeHttpRequests((authorize) ->
                // 특정 페이지 로그인 검사 여부 결정 가능
                // url 입력 후 검사 여부 설정
                authorize.requestMatchers("/**").permitAll()
        );
        return http.build();
    }
}
