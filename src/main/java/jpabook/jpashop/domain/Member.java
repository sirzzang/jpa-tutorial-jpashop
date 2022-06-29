package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 내장 타입
    private Address address;

    @OneToMany(mappedBy = "member") // 하나의 회원이 여러 주문, Order 테이블 Member 필드
    private List<Order> orders = new ArrayList<>();

}


// 객체는 변경 포인트가 2군데인데(Order.member, Member.member_id),
// foreign key 하나만 변경해야 함
// JPA 규약에 따라 연관관계 주인
// 연관관계 주인: foreign key가 가까운
