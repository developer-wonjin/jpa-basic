package hellojpa.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString(of = {"id", "name"})
@Entity
public class Team {

    @Id @GeneratedValue
    private Long id;
    private String name;

//    @BatchSize(size = 100)
    @OneToMany(mappedBy = "team")
    @Builder.Default
    private List<Member> members = new ArrayList<>();
}
