package com.leesh.dtos;

import com.leesh.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearchDto {
    //회원이름
    private String memberName;
    //주문상태
    private OrderStatus orderStatus;
}
