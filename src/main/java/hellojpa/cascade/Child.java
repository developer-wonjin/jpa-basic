package hellojpa.cascade;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@Entity
public class Child {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    private Parent parent;

    public Child(Long id, String name, Parent parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;

        if (parent != null) {
            parent.getChildren().add(this);
        }
    }
}
