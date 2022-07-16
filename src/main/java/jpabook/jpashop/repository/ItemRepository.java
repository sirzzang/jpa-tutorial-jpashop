package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    // 상품 저장(update)
    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            // 이미 영속되어 있거나, 다른 곳에서 가져왔을 경우
            em.merge(item);
        }
    }

    // 상품 단건 조회
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    // 상품 다건 조회
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
