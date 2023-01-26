package com.leesh.domains;

import lombok.Getter;
import org.springframework.aop.framework.adapter.AdvisorAdapterRegistrationManager;

import javax.persistence.*;

@Entity
@Getter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    //private Order order;

    @Embedded //@Embeddable 내장타입 클래스 사용시 붙여줌.
    private Address address;

    //private DeliveryStatus deliveryStatus;

}
