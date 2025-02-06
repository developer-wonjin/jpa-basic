package hellojpa;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Builder
@Getter @Setter
@Table(name = "ORDERS")
@Entity
public class Order extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    private Delivery delivery;

    public Order(Long id, Member member, LocalDateTime orderDate, OrderStatus status, List<OrderItem> orderItems, Delivery delivery) {
        this.id = id;
        this.member = member;
        this.orderDate = orderDate;
        this.status = status;
        this.orderItems = orderItems;
        this.delivery = delivery;
        if (member != null) {
            member.getOrders().add(this);
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", member=" + member.getId() +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", orderItems=" + orderItems +
                '}';
    }
}
