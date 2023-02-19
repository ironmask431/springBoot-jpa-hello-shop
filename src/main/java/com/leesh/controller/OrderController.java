package com.leesh.controller;
import com.leesh.domains.Item;
import com.leesh.domains.Member;
import com.leesh.domains.Order;
import com.leesh.dtos.OrderSearch;
import com.leesh.service.ItemService;
import com.leesh.service.MemberService;
import com.leesh.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    //@RequestParam으로 필드를 각각 매핑 받거나
    //@ModelAttribute (쿼리 parameter or form 타입으로 넘어 올 시)
    //@RequestBody (json타입으로 넘어올시) 를 사용해 dto로 매핑 받을 수 있다.
    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count){
        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch")OrderSearch orderSearch, Model model){
        List<Order> orders = orderService.findOrdrers(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}