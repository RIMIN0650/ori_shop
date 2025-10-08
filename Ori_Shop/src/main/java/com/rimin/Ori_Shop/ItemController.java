package com.rimin.Ori_Shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    @GetMapping("/item/list")
    String itemList(Model model){
        List<Item> itemList = itemRepository.findAll();
        System.out.println(itemList);
        model.addAttribute("itemList", itemList);
//        var a = new Item();
//        System.out.println(a.toString());
        return "/list.html";
    }

}
