package com.leesh.service;

import com.leesh.domains.Item;
import com.leesh.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public long save(Item item){
        return itemRepository.save(item);
    }

    public List<Item> finaAll(){
        return itemRepository.finaAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }


}
