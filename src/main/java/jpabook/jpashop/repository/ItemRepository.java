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
            // 준영속 엔티티
            /**
             * - 이미 영속되어 있거나, 다른 곳에서 가져왔을 경우
             * - merge의 반환 값은 영속성 컨텍스트로 관리됨
             *   그러나 merge의 파라미터는 영속성 컨텍스트로 관리되지 않음
             * - merge 시 모든 속성이 변경되므로, null로 저장될 수도 있음
             */
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
