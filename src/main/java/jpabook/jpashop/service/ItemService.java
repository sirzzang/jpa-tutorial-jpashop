package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
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
    // TODO: Controller에서 바로 엔티티에 접근해서 사용해도 되는가
    private final ItemRepository itemRepository;

    // 아이템 저장
    @Transactional(readOnly = false)
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    /**
     * 아이템 수정
     * 1. 변경 감지
     * 2. merge: 준영속 상태의 엔티티를 영속 상태로 변경하기 위해 사용
     */
    // 변경 감지(권장)
    @Transactional
    public Item updateItem(Long itemId, String name, int price, int stockQuantity) {

        // 영속 상태의 아이템 엔티티
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);

        // 트랜잭션 커밋 시 변경 감지
        return findItem;
    }

    // 병합(비권장)
    /**
     * {@link ItemRepository}에서 save 시 준영속 상태에서 호출하던 것
     * JPA 입장에서 변경
     */

    // 아이템 단건 조회
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    // 아이템 다건 조회
    public List<Item> findItems() {
        return itemRepository.findAll();
    }


}
