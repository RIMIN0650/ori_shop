package com.rimin.Ori_Shop.comment;

import com.rimin.Ori_Shop.member.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    @PostMapping("/comment")
    String postComment(@RequestParam String content,
                       @RequestParam Long parentId,
                       Authentication auth){
        CustomUser customUser = (CustomUser) auth.getPrincipal();
        var data = new Comment();
        data.setContent(content);
        data.setUsername(customUser.getUsername());
        data.setItemId(parentId);
        commentRepository.save(data);
        return "redirect:/main/home";
    }


}
