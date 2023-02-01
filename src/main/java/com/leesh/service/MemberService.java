package com.leesh.service;

import com.leesh.domains.Member;
import com.leesh.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    /**
     * 회원가입
     */
    public Long join(Member member){
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


    /**
     * 회원 전체 조회
     */
    public List<Member> findMember(){
        return memberRepository.finalAll();
    }

    /**
     * 단일 회원 조회
     */
    public Member findOne(Long memberId){
        return memberRepository.find(memberId);
    }
}
