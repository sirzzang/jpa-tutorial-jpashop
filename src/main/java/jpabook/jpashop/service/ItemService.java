package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    // ItemRepository에 위임만 하는 목적으로서의 서비스
    // Controller에서 바로 서비스에 접근해서 사용해도 되는가
    private final ItemRepository itemRepository;

    // 아이템 저장
    @Transactional(readOnly = false)
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    // 아이템 단건 조회
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    // 아이템 다건 조회
    public List<Item> findAll() {
        return itemRepository.findAll();
    }


}
