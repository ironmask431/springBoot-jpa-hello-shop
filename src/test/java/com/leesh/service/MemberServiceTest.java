package com.leesh.service;

import com.leesh.domains.Member;
import com.leesh.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class) // Junit4 에서 사용 하던것, Junit 5에서는 생략가능함.
//@ExtendWith(SpringExtension.class) 가 @SpringBootTest 에 포함되어 있으므로 @RunWith(SpringRunner.class) 생략가능
@SpringBootTest
@Transactional //@Transactional 을 @SpringBootTest 와 함께 사용될때는 기본적으로 각테스트시 마다 롤백하도록 되어있음.
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    //@Rollback(false) //transaction 롤백을 하지 않고싶을때.
    public void 회원가입() {
        //given
        Member member = new Member();
        member.setName("lee");

        //when
        Long savedId = memberService.save(member);

        //@Transactional 안에서는 같은 영속성 컨텍스트가 걸리므로 id가 같은 엔티티는 동일한 엔티티로 취급된다.
        //then
        Assertions.assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.save(member1);
        try{
            memberService.save(member2); //IllegalStateException 예외가 발생해야 한다. (동일한 이름)
        }catch (IllegalStateException e){
            return;
        }
        //then
        fail("IllegalStateException 이 발생해야 한다."); //여기까지 도달하면 테스트 실패.
    }
}