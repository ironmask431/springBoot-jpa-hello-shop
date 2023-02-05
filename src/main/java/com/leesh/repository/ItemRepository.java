package com.leesh.repository;


import com.leesh.domains.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public long save(Item item){
        //id가 없는 신규생성 item일 경우 persist, id가 있는 기존객체일 경우 merge
        if(item.getId() == null){
            em.persist(item);
        }else{
            em.merge(item);
        }
        return item.getId();
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> finaAll(){
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }




}
