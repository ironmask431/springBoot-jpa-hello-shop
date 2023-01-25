package com.leesh.domains;

import lombok.Getter;

import javax.persistence.*;

@Entity
//Inheritance 상속관계 전략
// 1. Single_table : 한테이블에 여러속성을 한번에 다 입력함.(무비, 앨범, 북..)
// 2. Table per Class : 무비, 북, 앨범을 각각의 테이블로 나누는 전략
// 3. Joined : 가장 정규화된 스타일?
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype") //Item 테이블안에서 movie,book,album을 어떻게 구분할지 설정. (SINGLE_TABLE 전략을 사용했으므로..)
@Getter
//구현체를 만들어서 사용할거라서 abstract(추상) 으로 선언
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

}
