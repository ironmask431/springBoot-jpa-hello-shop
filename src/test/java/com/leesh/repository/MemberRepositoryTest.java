package com.leesh.repository;

import com.leesh.domains.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    //기본적으로 @Test 어노테이션에서 실행된 쿼리들은 모두 자동 롤백처리된다.
    //롤백하지 않으려면 @Rollback(false) 어노테이션필요
    //테스트 메소드안에서 DB insert,update,delete 동작 필요할 시 @Transactional로 영속성 컨텍스트 부여

    //첫 테스트 실행 시 "No EntityManager with actual transaction available for current thread" 에러 발생
    //원인 -> 모든 엔티티 데이터 입력, 변경은 트랜잭션안에서만 이루어지므로. @Transactional 어노테이션필요함.
    @Test
    @Transactional
    void save() {
        //given
        Member member = new Member();
        member.setName("nameA");

        //when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.findOne(savedId);

        //then
        Assertions.assertEquals(savedId, findMember.getId());
        Assertions.assertEquals(member.getName(), findMember.getName());
    }

    @Test
    void find() {

    }
}