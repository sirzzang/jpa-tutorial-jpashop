package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable // 내장 타입
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {}

    // 값 타입이므로 변경할 수 없도록 생성자만 두고, 수정자는 제공하지 않는다
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
