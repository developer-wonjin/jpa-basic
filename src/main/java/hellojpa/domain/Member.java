package hellojpa.domain;

import hellojpa.value.Address;
import hellojpa.value.Period;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"id", "name", "team"})
@Entity
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private int age;

    @Setter
    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @Embedded
    private Period period;

    @ElementCollection
    @CollectionTable(
            name = "ADDRESS"
            , joinColumns = @JoinColumn(name = "MEMBER_ID"))
    private List<Address> addressHistory = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @OneToMany
    private List<Order> orders = new ArrayList<>();

    public void changeTeam(Team team) {
        this.team = team;
        if (team != null) {
            team.getMembers().add(this);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
