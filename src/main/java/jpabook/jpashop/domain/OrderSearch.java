package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memberName; // 검색 조건 회원명
    private OrderStatus orderStatus; // 검색 조건 주문상태



}
