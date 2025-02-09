package hellojpa.domain;

import hellojpa.domain.item.Item;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class OrderItem {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    private int orderPrice;

    private int count;

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", order=" + order.getId() +
                ", item=" + item.getId() +
                ", orderPrice=" + orderPrice +
                ", count=" + count +
                '}';
    }
}
