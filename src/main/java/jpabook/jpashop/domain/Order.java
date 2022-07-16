package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // 양방향 연관관계 편의 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // 생성자를 통한 생성 방지
    protected Order() {}

    // 생성 메서드
    /**
     * 복잡한 연관관계 기반의 생성은 별도의 생성 메서드로 관리
     * 생성할 때부터 생성 메서드를 호출하도록(TODO: 정적 팩터리 생성 메서드 이런 건가)
     * 추후 생성 변경 시 해당 메서드만 변경하면 됨
     */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();

        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem: orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER); // 처음 상태 ORDER로 강제
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    // 비즈니스 로직
    /**
     * 주문 취소
     */
    public void cancel() {

        // 이미 배송 완료 시 주문 취소 불가
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        // 주문 상태 변경
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel(); // 주문 상품별 취소
        }
    }

    // 조회 로직
    /**
     * 전체 주문 상품 가격 조회
     */
    public int getTotalPrice() {
//        int totalPrice = 0;
//        for (OrderItem orderItem : orderItems) {
//            totalPrice += orderItem.getTotalPrice();
//        }
//        return totalPrice;
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
}
