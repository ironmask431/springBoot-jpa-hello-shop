package com.leesh.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DeliveryStatus {
    READY(0,"배송준비중"),
    COMP(0,"배송완료");

    private final int value;
    private final String name;
}
