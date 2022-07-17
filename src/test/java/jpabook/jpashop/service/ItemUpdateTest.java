package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception {

        Book book = em.find(Book.class, 1L);

        // TX
        book.setName("eraser");

        /**
         *  변경 감지: dirty checking
         * - 트랜잭션 안에서, 트랜잭션 커밋 시 JPA가 변경 감지 후 반영
         * - 예: {@link jpabook.jpashop.domain.Order}에서 Order 상태 변경 시, DB 값도 변경
         */

        /**
         * 준영속 엔티티의 변경
         * - 준영속 엔티티(데이터베이스에 영속된 적이 있음, 식별자가 있음)의 경우,
         * - JPA가 식별할 수 있는 id를 가지고 있고, 영속성 컨텍스트가 더 이상 관리하지 않음
         * - 따라서 엔티티 데이터가 변경되더라도 감지되지 않음
         * - 다음의 방법으로 수정해야 함: 1. (준영속 엔티티이지만) 변경감지 2. merge
         */
        
    }


}
