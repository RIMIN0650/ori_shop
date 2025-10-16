package com.rimin.Ori_Shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @GetMapping("/main/home")
    String itemList(Model model){

        List<Item> itemList = itemService.findTop3Items();
//        System.out.println(itemList);
        model.addAttribute("itemList", itemList);
//        var a = new Item();
//        System.out.println(a.toString());
        return "/main.html";
    }

    @GetMapping("/item/add")
    String addItemPage() {
        return "addItem.html";
    }

    @PostMapping("/item/add")
    String addItem(@RequestParam(name = "itemName") String itemName,
                   @RequestParam(name = "price") Integer price) {

        itemService.saveItem(itemName, price);
        return "redirect:/main/home";
    }

    @GetMapping("/item/detail/{itemId}")
    String itemDetail(@PathVariable Long itemId, Model model){

        try {
            Optional<Item> optionalItem = itemRepository.findById(itemId);
            if(optionalItem.isPresent()){
//              System.out.println(optionalItem.get());
                model.addAttribute("item", optionalItem.get());
            } else {
                return "redirect:/main/home";
            }
            // throw new Exception("에러 이유");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // return "redirect:/main/home";
            // ajax로 서버와 통신하면 redirect 사용불가
            // return ResponseEntity.status(400).body("your fault");
            // 사용하려면 return type을 ResponseEntity<String> 으로 변환하여 사용
            // 유저 잘못 4xx, 서버 잘못 5xx, 정상 200
            // status() 안에 HttpStatus.NOT_FOUND 와 같이 에러 이유를 입력하면 알아서 변환됨
        }


//        Optional<Item> optionalItem = itemRepository.findById(itemId);
//        if(optionalItem.isPresent()){
////            System.out.println(optionalItem.get());
//            model.addAttribute("item", optionalItem.get());
//        } else {
//            return "redirect:/main/home";
//        }

        return "detail.html";
    }




}
