package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        /**
         * 검증
         * - 서버 사이드에서 검증 실패 시 BindingResult에 데이터가 들어옴
         * - 검증 실패한 필드를 담아서 다시 보냄 -> html 단에서 처리 가능
         * - 기존 결과는 그대로 담겨 있음(기존 form이니까)
         */
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        // 회원 객체
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        // 회원 서비스에서 회원 가입
        memberService.join(member);

        return "redirect:/"; // 저장 후 redirect 쓰는 것이 좋음

    }

}
