package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    @DisplayName("상품을 주문한다")
    public void 상품주문() throws Exception {

        // given
        Member member = createMember();
        Book book = creteBook("어떻게 살 것인가", "유시민", "123123123", 10000, 10);

        // when
        int orderCount = 3;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("상품 주문 시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 수량이 정확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문 총액은 가격 * 수량이다", 10000 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어들어야 한다", 7, book.getStockQuantity());
    }



    @Test(expected = NotEnoughStockException.class)
    @DisplayName("재고 수량 초과 시 주문이 되지 않아야 한다")
    public void 상품주문_재고수량초과() throws Exception {

        // given
        Member member = createMember();
        Book book = creteBook("어떻게 살 것인가", "유시민", "123123123", 10000, 10);

        // when
        int orderCount = 20;
        orderService.order(member.getId(), book.getId(), orderCount);

        // then
        fail("재고 수량 부족 예외가 발생해야 한다");
    }

    @Test
    @DisplayName("")
    public void 주문취소() throws Exception {
        // given
        Member member = createMember();
        Book book = creteBook("어떻게 살 것인가", "유시민", "123123123", 10000, 10);

        // when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        orderService.cancelOrder(orderId);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("주문 취소 시 상태는 CANCEL", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품의 재고는 다시 복원되어야 한다.", 10, book.getStockQuantity());
    }

    // 테스트를 위한 아이템 엔티티 생성
    private Book creteBook(String name, String author, String isbn, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    // 테스트를 위한 회원 엔티티 생성
    private Member createMember() {
        Member member = new Member();
        member.setName("sirzzang");
        member.setAddress(new Address("경기도 성남시 분당구", "탄천상로 151번길", "123123"));
        em.persist(member); // 테스트용 영속
        return member;
    }
}