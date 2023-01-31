package com.leesh.domains;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 계층 구조 샘플
 */
@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue
    @Column(name="category_id")
    private Long id;

    private String name;

    //다:다 관계 예제. 실무에선 쓰기에는 부적합함.
    @ManyToMany(fetch = FetchType.LAZY)
    //다대다 관계에서는 category 와 item 사이에 중간테이블이 필요하다. (category_item)
    @JoinTable(
            name="category_item"
            ,joinColumns = @JoinColumn(name="category_id")
            ,inverseJoinColumns = @JoinColumn(name="item_id")
    )
    private List<Item> items = new ArrayList<>();

    //Category 게층구조에서 부모를 구현
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Category parent;

    //Category 게층구조에서 자식을 구현
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //셀프 연관관계설정용 편의메소드
    public void addChildCategory(Category category){
        this.child.add(category);
        category.setParent(this);
    }
}
