package hellojpa;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@ToString(of = {"id", "name", "team"})
@Entity
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @OneToMany
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Member(Long id, String name, String city, String street, String zipcode, Team team, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.team = team;
        this.orders = orders;
        if (team != null) {
            team.getMembers().add(this);
        }
    }
}
