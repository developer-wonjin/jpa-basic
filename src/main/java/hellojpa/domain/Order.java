package hellojpa.domain;

import hellojpa.value.Address;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "ORDERS")
@Entity
public class Order {

    @Id @GeneratedValue
    private Long id;

    private int OrderAmount;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(cascade = ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = ALL)
    private Delivery delivery;

    public void setMember(Member member) {
        this.member = member;
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
