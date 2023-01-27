package com.leesh.domains.items;

import com.leesh.domains.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("A") //item 테이블 안에서 Album 임을 구분하기 위한 구분값 설정
public class Album extends Item {
    private String artist;
    private String etc;
}
