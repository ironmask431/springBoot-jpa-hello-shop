package com.leesh.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//    추상클래스 entity의 경우 상속관계 전략. strategy 설정이 필요함. **
//    SINGLE_TABLE : 하위 클래스들을 하나의 테이블에 모두 입력
//    TABLE_PER_CLASS : Album, Book, Movie를 각각 테이블에 입력
//    JOINED : 일반적인 정규화된 스타일
@DiscriminatorColumn(name = "dtype")
@Getter
@Setter
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQty;


}
