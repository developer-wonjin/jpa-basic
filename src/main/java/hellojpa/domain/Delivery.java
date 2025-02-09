package hellojpa.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Delivery {

    @Id @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "delivery")
    private Order order;

    private String city;
    private String street;
    private String zipcode;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
}
