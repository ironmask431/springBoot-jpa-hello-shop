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

    @OneToMany(mappedBy = "member") // member 다 : 1 order
    //mappedBy 는 연관관계에서 주인이 아닐경우 설정함. member는 주인엔티티인 Order의 필드명으로 설정함.
    //읽기전용이됨. orders의 값을 바꾼다고 해서 데이터가 변경되지않음.
    private List<Order> orders = new ArrayList<Order>();
}
