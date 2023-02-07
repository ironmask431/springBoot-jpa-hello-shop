package com.leesh.domains;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 가격 (주문 상품 단가) - item의 가격과 따로 설정한이유 : 쿠폰할인등의 요소 고려
    private int count; //주문 수량

    //private 생성자 선언이유 = 외부에서 OrderItem 객체 생성 시 createOrderItem()을 통해서 생성하라고 강제로 알려주는 용도.
    //OrderItem orderItem = new OrderItem(); 으로 사용되는것을 방지!
    protected OrderItem(){

    }

    //==비즈니스 로직==//
    public void cancel() {
        this.item.addStock(count);
    }

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.reduceStock(count);
        return orderItem;
    }

    //==조회 로직==//
    public int getTotalPrice(){
        return this.orderPrice * this.count;
    }
}
