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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    //Post매핑의 경우 @RequestParam으로 필드를 각각 매핑 받거나
    //@RequestBody 를 사용해 dto로 매핑 받을 수 있다.
    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count){
        orderService.order(memberId, itemId, count);
        return "redirect:/";
    }
}