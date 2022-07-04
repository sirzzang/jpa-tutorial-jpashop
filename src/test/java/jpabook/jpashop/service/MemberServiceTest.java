package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // Test에 붙을 때만 롤백
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("")
    // @Rollback(false)
    public void 회원가입() throws Exception {

        /**
         * EntityManager에서 persist하더라도, (특히 GeneratedValue 전략에서는) JPA Insert문이 실행되지 않음
         * 데이터베이스 transaction이 commit될 때, 영속성 컨텍스트 flush되면서 실행됨
         * 그런데, Spring @Transactional은 rollback을 해버리기 때문에, 로그 상 Insert가 실행되지 않음
         * Insert되는 것을 보고 싶으면, rollback false 옵션을 주어야 한다
         */

        // given
        Member member = new Member();
        member.setName("sirzzang");

        // when
        Long savedId = memberService.join(member);

        // then
        em.flush(); // 강제로 쿼리. Rollback 없이 Insert문 볼 수 있음
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    @DisplayName("중복회원 회원가입 시 예외가 발생한다.")
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        memberService.join(member1);
        // then
        fail("IllegalStateException 예외가 발생해야 한다."); // 여기까지 오면 안됨
    }
}