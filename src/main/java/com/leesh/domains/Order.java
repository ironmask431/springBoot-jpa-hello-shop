package com.leesh.domains;

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
    @JoinColumn(name = "member_id") //Member 테이블의 어떤 DB컬럼으로 join할지 설정
    private Member member;


    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    //1:1 관계일때는 연관관계의 주인을 어느쪽에하든 관계없다.
    @OneToOne(fetch = FetchType.LAZY) // order 1 : 1 delivery 관계이므로.
    @JoinColumn(name = "delivery_id") //Delivery 테이블의 delivery_id 를 fk로 설정
    private Delivery delivery;

    private LocalDateTime orderDateTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문상태 ORDER, CANCEL
}
