package com.leesh.domains;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name="orders") //실제 DB테이블명 다르게 적용 시
@Getter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id") //실제 DB컬럼명 다르게 설정필요할 시
    private Long id;

    @ManyToOne //Order 다 : 1 Member 관계이므로
    @JoinColumn(name = "member_id") //Member 테이블의 어떤 DB컬럼으로 join할지 설정
    private Member memeber;
}
