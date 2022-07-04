package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    // 변경을 막기 위해 final로 선언
    private final MemberRepository memberRepository;

    // 회원 가입
    @Transactional // @Transactional(readOnly = false)
    public Long join(Member member) {
        // 중복 회원 체크
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 한 회원 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    /**
     * 중복 회원 조회 후 검증
     * @param member 중복 여부를 검증할 회원
     * WAS 여러 대 뜨는 경우, 동시에 회원가입 시 문제가 될 수 있음
     * 실무에서는 최선의 방어를 위해, member에 unique 제약 조건 잡는 게 좋음
     */
    private void validateDuplicateMember(Member member) {
        List<Member> members = memberRepository.findByName(member.getName());
        if (!members.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
