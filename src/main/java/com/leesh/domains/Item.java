package com.leesh.domains;

import com.leesh.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//Inheritance 상속관계 전략
// 1. Single_table : 한테이블에 여러속성을 한번에 다 입력함.(무비, 앨범, 북..)
// 2. Table per Class : 무비, 북, 앨범을 각각의 테이블로 나누는 전략
// 3. Joined : 가장 정규화된 스타일?
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype") //Item 테이블안에서 movie,book,album을 어떻게 구분할지 설정. (SINGLE_TABLE 전략을 사용했으므로..)
@Getter
@Setter
//구현체를 만들어서 사용할거라서 abstract(추상) 으로 선언
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    //다:다 관계 예제. 실무에선 쓰지마세요.
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비즈니스 로직==//
    //객체지향적인 설계 : 해당 엔티티관련 비즈니스 로직은 엔티티에 설정해놓는게 응집도가 높다.
    //객체지향적인 관점에서 좋지않은 설계 : 서비스단에서 재고를 더하고, 빼는 로직을 수행 후 재고를 엔티티에 setter 해주는 형식.
    public void addStock(int qty){
        this.stockQuantity += qty;
    }

    public void reduceStock(int qty){
        if(this.stockQuantity < qty){
            throw new NotEnoughStockException("stock is not enough");
        }
        this.stockQuantity -= qty;
    }




}
