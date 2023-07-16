package com.leesh.domains;

import com.leesh.enums.DeliveryStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    //mappedBy = 연관관계의 하인. 이 필드값은 Order 테이블의 delivery 필드값에 의해 변경됨을 의미.
    //이 필드값을 직접변경해도 DB 업데이트가 일어나지않는다.
    private Order order;

    @Embedded //@Embeddable 내장타입 클래스 사용시 붙여줌.
    private Address address;

    //@Enumerated(EnumType.ORDINAL) //default타입.  enum 멤버의 순서(숫자)값이 DB에 insert 된다. 멤버의 순서가 바뀔경우 숫자값이 바뀌므로 사용 비추천.
    @Enumerated(EnumType.STRING) //enum 문자열 그대로 insert 된다.
    private DeliveryStatus deliveryStatus; //READY(배송중), COMP(배송완료)

}
