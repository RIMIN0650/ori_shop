package com.rimin.Ori_Shop.sales;

import com.rimin.Ori_Shop.member.domain.CustomUser;
import com.rimin.Ori_Shop.sales.domain.Sales;
import com.rimin.Ori_Shop.sales.repository.SalesRepository;
import com.rimin.Ori_Shop.sales.service.SalesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SalesController {

    private final SalesRepository salesRepository;
    private final SalesService salesService;

    @PostMapping("/order")
    String postorder(@RequestParam String itemName,
                     @RequestParam Integer price,
                     @RequestParam Integer count,
                     Authentication auth) {
        Sales sales = new Sales();
        sales.setItemName(itemName);
        sales.setPrice(price);
        sales.setCount(count);
        CustomUser user = (CustomUser) auth.getPrincipal();
        sales.setMemberId(user.id);
        salesRepository.save(sales);

        return "redirect:/main/home";
    }


    // 모든 주문내역 보여주는 페이지
    @GetMapping("/order/all")
    String getAllOrder(Model model){
        List<Sales> salesList = salesService.getAllOrderList();
        model.addAttribute("orderList", salesList);
        return "orderList.html";
    }


}
