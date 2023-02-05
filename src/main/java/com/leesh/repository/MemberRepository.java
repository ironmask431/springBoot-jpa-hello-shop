package com.leesh.repository;

import com.leesh.domains.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //@PersistenceContext //스프링이 관리하는 EntityManager 를 주입해줌.
    //private EntityManager em;

    //spring boot 에서 EntityManager autowired 를 지원해주기때문에 아래 형식으로 변경가능
    private final EntityManager em;

    //cmd + shift + T : 자동으로 해당 메소드 테스트코드를 생성해줌. (IDE단축키)
    public long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    //jpql 의 특징 : 엔티티를 기준으로 조회한다. (실제쿼리는 테이블을 기준으로 조회한다는 점과 차이점이 있음.)
    public List<Member> finalAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}

