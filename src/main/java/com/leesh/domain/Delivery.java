package com.leesh.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    //1:1관계 - 다른엔티티에도 동일하게 @OneToOne 달아주면됨.
    //연관관계 주인을 Order 엔티티로 설정했으므로, Delivery의 order 필드에는 mappedBy 설정함.
    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    //@Enumerated(EnumType)은 꼭 STRING 으로 써야함. ORDINARY로 하게되면 숫자로 insert됨..
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //배송상태 enum - READY, COMP
}
