package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter // Setter 없으면 template에서 접근 불가능
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수입니다")
    private String name;

    private String city;
    private String street;
    private String zipcode;


}
