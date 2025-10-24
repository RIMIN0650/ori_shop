package com.rimin.Ori_Shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {



    /*
    유저가 아이디와 비밀번호를 입력하면 유저가 제출한 username/password가 UserDetailsService에 도착하는데
    DB에서 동일 아이디의 유저 정보를 꺼내면 유저가 제출한 비밀번호와 DB 비밀번호를 비교, 내부적으로 passwordEncodier 해싱해서 비교
    이상이 없으면 입장권용 쿠키 하나 발급해서 유저에게 보내줌
    누가 어떤 이름으로 언제 로그인했는지 세션 데이터도 생성해서 보관함
    앞으로 유저가 입장권을 제출하면 이것을 세션 데이터와 비교하여 로그인 여부 판단 가능
    */

    // DB에서 username을 가진 사용자를 찾아서
    // return new User(유저아이디, 비번, 권한)

    var result = memberRepository.findByUsername(username);
    if(result.isEmpty()){
        throw new UsernameNotFoundException("id not found");
    }
    var member = result.get();

    List<GrantedAuthority> auth = new ArrayList<>();
    auth.add(new SimpleGrantedAuthority("user"));
    var customUser = new CustomUser(member.getUsername(), member.getPassword(), auth);
    customUser.displayName = member.getDisplayName();
    return customUser;

    }

}

