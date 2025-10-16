package com.rimin.Ori_Shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("/user/join")
    String join() {
        return "member/join.html";
    }


    @PostMapping("/user/join")
    String addMember(String username, String password, String displayName) {

        Member member = new Member();
        member.setUsername(username);
//        var hashedPassword = new BCryptPasswordEncoder().encode(password);
        var hashedPassword = passwordEncoder.encode(password);
        member.setPassword(hashedPassword);
        member.setDisplayName(displayName);
        memberRepository.save(member);
        return "redirect:/main/home";
    }

    @GetMapping("/login")
    public String login() {
        var result = memberRepository.findByUsername("chris0540");
        System.out.println(result.get().getDisplayName());
        return "member/login.html";
    }



}
