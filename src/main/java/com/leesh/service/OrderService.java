package com.leesh.service;

import com.leesh.domains.*;
import com.leesh.repository.ItemRepository;
import com.leesh.repository.MemberRepository;
import com.leesh.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        //order 만 jpa 로 저장을 하더라도 위에서 생성한 delivery, orderItem 도 자동으로 저장이 된다.
        //자동으로 저장 되는 이유는 Order 엔티티내의 Delivery 와 List<OrderItem> 에 cascade=ALL 옵션이 걸려있기 때문.

        return order.getId();
    }

    //취소

    //검색
}
