package com.leesh.Repository;

import com.leesh.domains.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    //cmd + shift + T : 자동으로 해당 메소드 테스트코드를 생성해줌. (IDE단축키)
    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }
}

