package com.leesh.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded //내장타입으로 매핑을 할경우. (Address의 여러 필드들도 Member테이블의 필드가됨.)
    private Address address;

    // Member 1 : 다 Order
    @OneToMany(mappedBy = "member") //여기서 member는 Order 주인 엔티티의 필드명
    //mappedBy 는 연관관계에서 노예 일 경우 설정함.(해당 엔티티가 다:1 에서 1일때)
    //읽기전용이됨. 여기 orders의 값을 바꾼다고 해서 연관관계가(데이터) 변경되지않음.
    //주인엔티티(Order엔티티의 member) 값이 변경되야 데이터수정됨.
    private List<Order> orders = new ArrayList<Order>();
}
