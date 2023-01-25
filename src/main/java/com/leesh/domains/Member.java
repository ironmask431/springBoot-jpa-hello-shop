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

    @OneToMany(mappedBy = "member") //Order 다 : 1 Member 관계이므로
    //mappedBy : 연관관계의 하인. Order 엔티티의 member 필드값에 의해 변경되어진다 라는것을 의미함.
    //연관관계의 하인이므로 orders 의 값을 직접변경한다고해서 DB데이터가 변경되지 않는다.
    private List<Order> orders = new ArrayList<>();




}
