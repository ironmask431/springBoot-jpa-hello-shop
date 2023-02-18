package com.leesh.controller;
import com.leesh.domains.Item;
import com.leesh.domains.Member;
import com.leesh.service.ItemService;
import com.leesh.service.MemberService;
import com.leesh.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createOrderForm(Model model){

        List<Member> members = memberService.finalAll();
        List<Item> items = itemService.finaAll();

        model.addAttribute("members",members);
        model.addAttribute("items",items);

        return "/order/orderForm";
    }
}
