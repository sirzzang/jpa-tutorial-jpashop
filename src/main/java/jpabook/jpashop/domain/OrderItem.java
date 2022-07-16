package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격

    private int count; // 주문 수량

    // 생성자를 통한 생성 방지
    protected OrderItem() {}

    // 생성 메서드
    /**
     * 주문 상품 생성
     * - 쿠폰 받거나 할인하거나 하면 상품의 가격과 다를 수 있음
     */
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();

        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        // item 재고 감소: Order 생성 메서드에서 OrderItem 생성 메서드 타게 되면서 재고 감소
        item.removeStock(count);

        return orderItem;
    }

    // 비즈니스 로직
    /**
     * 주문 상품 취소
     */
    public void cancel() {
        // item 재고 원상 복귀
        getItem().addStock(count);
    }

    // 조회 로직
    /**
     * 주문 상품별 총액
     * Getter 사용: https://www.inflearn.com/questions/20180
     */
    public int getTotalPrice() {
        // return getOrderPrice() * getCount();
        return orderPrice * count;
    }
}
