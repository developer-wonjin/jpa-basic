package hellojpa;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
public class Movie extends Item {

    private String director;
    private String actor;

}
