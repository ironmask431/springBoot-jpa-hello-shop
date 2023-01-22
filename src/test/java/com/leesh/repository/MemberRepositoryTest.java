package com.leesh.repository;

import com.leesh.Repository.MemberRepository;
import com.leesh.domains.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    void save() {
        //given
        Member member = new Member();
        member.setUsername("nameA");

        //when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        //then
        Assertions.assertEquals(savedId, findMember.getId());
        Assertions.assertEquals(member.getUsername(), findMember.getUsername());

        //첫 테스트 실행 시 "No EntityManager with actual transaction available for current thread" 에러 발생
        //원인 -> 모든 엔티티 데이터 입력, 변경은 트랜잭션안에서만 이루어지므로. @Transactional 어노테이션필요함.
    }

    @Test
    void find() {

    }
}