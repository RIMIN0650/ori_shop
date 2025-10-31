package com.rimin.Ori_Shop.member;

import com.rimin.Ori_Shop.member.domain.CustomUser;
import com.rimin.Ori_Shop.member.domain.Member;
import com.rimin.Ori_Shop.member.repository.MemberRepository;
import com.rimin.Ori_Shop.sales.domain.Sales;
import com.rimin.Ori_Shop.sales.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final SalesService salesService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


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
//        var result = memberRepository.findByUsername("chris0540");
//        System.out.println(result.get().getDisplayName());
        return "member/login.html";
    }


    @GetMapping("/user/my-page")
    public String myPage(Authentication auth) {
        System.out.println(auth);
        System.out.println(auth.getName());
        System.out.println(auth.isAuthenticated()); // 현재 로그인 여부 확인
//        if(auth.isAuthenticated()) : 로그인되어있는 경우에만 마이페이지로 연결해주세요
        System.out.println(auth.getAuthorities().contains(new SimpleGrantedAuthority("user")));
        CustomUser customUser = (CustomUser) auth.getPrincipal();
        System.out.println(customUser.displayName);
        return "member/mypage.html";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/main/home";
    }

    
    // 로그인 한 사용자의 주문목록 보여주기
    @GetMapping("/user/my-order")
    String getUserOrder(Model model, Authentication auth){
        CustomUser customUser = (CustomUser) auth.getPrincipal();
        List<Sales> salesList = salesService.findSalesByUserName(customUser.id);
        model.addAttribute("salesList", salesList);
        return "member/myOrder.html";
    }


    @PostMapping("/login/jwt")
    @ResponseBody
    public String getUser(@RequestBody Map<String, String> data, ){
        // 로그인시켜주세요
        // jwt 입장권도 보내주세요

        var authToken = new UsernamePasswordAuthenticationToken(data.get("username"), data.get("password"));
        // 아이디 비밀번호를 DB 내용과 비교해서 로그인해줌
        var auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        // 로그인이 위와 같이 수동으로 하는 경우 auth 변수에 유저정보가 반영이 안됨.
        // 직접 넣어주어야 함



        SecurityContextHolder.getContext().setAuthentication(auth);
        // auth 변수에 마음대로 유저정보 추가 기능

        //SecurityContextHolder.getContext().getAuthentication()

        return "";
    }


}
