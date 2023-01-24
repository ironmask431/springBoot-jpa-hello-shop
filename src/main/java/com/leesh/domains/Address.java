package com.leesh.domains;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable //내장타입으로 선언
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
