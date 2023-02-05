package com.leesh.service;

import com.leesh.domains.Member;
import com.leesh.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    //필드를 Autowired 어노테이션으로 주입하는것보다 생성자(@RequiredArgsConstructor)로 주입할때 장점1. -
    //어떤 필드가 필요한지 직관적으로 알수 있어서 좋음.
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    /**
     * 중복회원 검증
     */
    private void validateDuplicateMember(Member member){
        //여기서는 전체 멤버를 조회하는것보다 exists 를 사용하면 더 성능향상 가능.
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원 입니다.");
        }
    }

    public List<Member> finalAll(){
        return memberRepository.finalAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
