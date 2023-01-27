package com.leesh.domains.items;

import com.leesh.domains.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("B") //item 테이블 안에서 book 임을 구분하기 위한 구분값 설정
public class Book extends Item {
    private String author;
    private String isbn;
}

