package com.leesh.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;

    /*
    *** 외래키가 있는 곳을 연관관계의 주인으로 정해라.
    연관관계의 주인은 단순히 외래키를 누가 관리하냐의 문제이지 비즈니스상 우위에 있다고 주인으로 정하면안된다.
    예를들어 자동차와 바퀴가 있다면 "일대다" 관계에서 항상 "다" 쪽에 외래키가 있으므로, 외래키가 있는 바퀴를
    연관관계의 주인으로 정하면 된다. (관계변경시 주인 엔티티의 값만 변경해주면됨.)
    */

    @ManyToOne //Order 다 : Member가 1 = 그러므로 Order가 연관관계의 주인이됨.
    //연관관계의 주인엔티티의 필드에 @JoinColumn을 선언
    @JoinColumn(name="member_id") //member 테이블에서 조인할 컬럼명
    private Member member;

    @OneToMany(mappedBy = "order") // Order 1 : 다 OrderItem (주인)
    private List<OrderItem> orderItems = new ArrayList<>();

    //1:1관계 - 다른엔티티에도 동일하게 @OneToOne 달아주면됨.
    //@OneToOne 일때는 fk를 어디에 둬도 상관없으나, 더 많이 접근하는 엔티티에 두는게 보통 (연관관계 주인으로 설정)
    @OneToOne
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간

    //@Enumerated(EnumType)은 꼭 STRING 으로 써야함. ORDINARY로 하게되면 숫자로 insert됨..
    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 enum [ORDER,CANCEL]
}
