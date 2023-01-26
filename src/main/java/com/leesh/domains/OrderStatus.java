package com.leesh.domains;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {
    ORDER(1,"주문완료"),
    CANCEL(2,"주문취소");

    private final int value;
    private final String name;
}
