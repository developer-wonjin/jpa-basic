package hellojpa.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int price;
    private int stockAmount;

}
