package com.leesh.domain;

import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Embeddable;

@Embeddable //Member의 내장타입 클래스
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
