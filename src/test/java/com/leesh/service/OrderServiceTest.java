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
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.fail;


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

        Assert.assertEquals("상품 주문 시 주문상태는 ORDER", OrderStatus.ORDER, order.getOrderStatus());
        Assert.assertEquals("주문한 상품 종류수 확인",1,order.getOrderItems().size());
        Assert.assertEquals("총 주문금액은 가격 * 수량",1000 * orderCount, order.getTotalPrice());
        Assert.assertEquals("주문수량만큼 재고 차감", 8, book.getStockQuantity());

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
        Assert.assertEquals("주문취소시 주문상태는 취소여야함.",OrderStatus.CANCEL,order.getOrderStatus());
        Assert.assertEquals("재고는 원복되어야 한다",10,book.getStockQuantity());
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
