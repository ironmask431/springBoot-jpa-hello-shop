package com.leesh.domains;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @GeneratedValue @Id
    @Column(name="member_id") //실제 DB컬럼명 다르게 설정필요할 시
    private Long id;

    private String name;

    @Embedded //내장타입 사용함을 적용
    private Address address;

    @OneToMany //Order 다 : 1 Member 관계이므로
    private List<Order> orders = new ArrayList<>();




}
