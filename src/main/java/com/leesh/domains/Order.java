package com.leesh.domains;

import com.leesh.enums.DeliveryStatus;
import com.leesh.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders") //실제 생성할 DB 테이블명
@Getter
@Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id") //실제 DB컬럼명 다르게 설정필요할 시
    private Long id;

    /**
     * N+1 문제를 피하기 위해 @ManyToOne, @OneToOne 은 모두 (fetch = FetchType.LAZY) 으로 설정한다. (기본은 EAGER로 되어있음.)
     */
    //joinColumn(fk필드) 을 설정한곳이 연관관계의 주인. 두 엔티티의 관계를 변경하려면 연관관계 주인엔티티의 값을 변경하면 된다.
    @ManyToOne(fetch = FetchType.LAZY) //Order 다 : 1 Member 관계이므로
    @JoinColumn(name = "member_id") //Member 테이블의 member_id 를 fk로 설정하여 실제 ORDERS 테이블에 member_id 라는 컬럼이 생성된다.
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    // mappedBy 가 적용되있으므로 ** 실제 ORDERS 테이블에는 orderItems 라는 컬럼이 생성되지 않는다. **
    private List<OrderItem> orderItems = new ArrayList<>();

    //1:1 관계일때는 연관관계의 주인을 어느쪽에하든 관계없다.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // order 1 : 1 delivery 관계이므로.
    @JoinColumn(name = "delivery_id") //Delivery 테이블의 delivery_id 를 fk로 설정하여 실제 ORDERS 테이블에 delivery_id 라는 컬럼이 생성된다.
    private Delivery delivery;

    private LocalDateTime orderDateTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문상태 ORDER, CANCEL

    //==양방향 연관관계 설정 메소드==//
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }
    public void setOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for(OrderItem orderItem : orderItems){
            order.setOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDateTime(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    //주문취소
    public void cancel(){
        if(this.delivery.getDeliveryStatus().equals(DeliveryStatus.COMP)){
            throw new IllegalStateException("배송 완료가 된 상품은 취소가 불가능합니다.");
        }
        this.setOrderStatus(OrderStatus.CANCEL);

        //재고 원복
        for(OrderItem orderItem : this.orderItems){
            orderItem.cancel();
        }

    }

    //== 조회 로직 ==//
    //전체 주문가격 조회
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }








}
