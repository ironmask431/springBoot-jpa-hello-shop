package com.leesh.domains;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable //내장타입으로 선언
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    //protected 를 사용하여 jpa 스펙상 만들어놓은 기본생성자 임을 표시
    protected Address() {
    }

    //@Embeddable을 사용한 값타입은 변경 불가능하게 설계해야한다. @setter 제거.
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
