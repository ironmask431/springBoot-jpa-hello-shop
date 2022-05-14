package com.leesh.domain;

import com.leesh.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter

public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    private Item item;

    @ManyToOne // OrderItem 다 : 1 order
    @JoinColumn(name="order_id") // 조인할 order 엔티티의 컬럼명
    private Order order;

    private int orderPrice; //주문시 가격
    private int count; //개수

}
