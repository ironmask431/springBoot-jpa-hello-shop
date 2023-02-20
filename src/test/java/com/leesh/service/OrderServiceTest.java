package com.leesh.service;

import com.leesh.domains.Address;
import com.leesh.domains.Member;
import com.leesh.domains.Order;
import com.leesh.domains.items.Book;
import com.leesh.enums.OrderStatus;
import com.leesh.exception.NotEnoughStockException;
import com.leesh.repository.ItemRepository;
import com.leesh.repository.MemberRepository;
import com.leesh.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;


    /**
     * 주의사항 ***
     * @Test 어노테이션 import 주의
     * import org.junit.jupiter.api.Test; (O)
     * import org.junit.Test; (X) 이걸로하면 에러..!!
     */

    @Test
    public void 상품주문(){
        //given
        Member member = createMemeber("수환","서울","광진구","1234");
        Book book = createBook("책책",1000,10);

        int orderCount = 2;

        //when
        Long orderId =  orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order order = orderRepository.findOne(orderId);

        Assertions.assertEquals(OrderStatus.ORDER, order.getOrderStatus());
        Assertions.assertEquals(1,order.getOrderItems().size());
        Assertions.assertEquals(1000 * orderCount, order.getTotalPrice());
        Assertions.assertEquals( 8, book.getStockQuantity());

    }

    @Test
    public void 상품주문_재고부족(){
        //given
        Member member = createMemeber("수환","서울","광진구","1234");
        Book book = createBook("책책",1000,10);

        int orderCount = 11;

        //when
        try {
            orderService.order(member.getId(), book.getId(), orderCount);
        }catch (NotEnoughStockException e){
            return;
        }

        //then
        fail("재고수량 부족 exception이 발생해야 한다.");

    }

    @Test
    public void 주문취소(){
        //given
        Member member = createMemeber("수환","서울","광진구","1234");
        Book book = createBook("책책",1000,10);
        int orderCount = 2;
        Long orderId =  orderService.order(member.getId(), book.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order order = orderRepository.findOne(orderId);
        Assertions.assertEquals(OrderStatus.CANCEL,order.getOrderStatus());
        Assertions.assertEquals(10,book.getStockQuantity());
    }

    public Member createMemeber(String name, String city, String street, String zipcode){
        Member member = new Member();
        member.setName(name);
        member.setAddress(new Address(city,street,zipcode));
        memberRepository.save(member);
        return member;
    }

    public Book createBook(String name, int price, int stock){
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stock);
        itemRepository.save(book);
        return book;
    }



}
