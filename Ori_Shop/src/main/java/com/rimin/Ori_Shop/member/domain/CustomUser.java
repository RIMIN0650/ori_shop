package com.rimin.Ori_Shop.member.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {
    // extends : User 안에 있던거 복사해주세요
// extends 문법 사용하려면 super를 사용해야 한다.
// super : 복사해온 클래스를 의미, 뒤에 소괄호가 있으면 extends로 복사한 클래스의 constructor
    public String displayName;
    public Long id;
    public CustomUser(
            // CustomUser에서 새로운 object를 뽑을 때
            // 아래의 3개의 파라미터를 입력받을 수 있도록 함
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities
    ) { // user 클래스의 constructor
        super(username, password, authorities);
    }
}