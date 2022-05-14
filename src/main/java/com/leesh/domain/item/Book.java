package com.leesh.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") //상속관계 전략이 SINGLE_TABLE 일 경우 설정하는 구분값
@Getter
@Setter
public class Book extends Item {
    private String author;
    private String isbn;
}
