package hellojpa;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
public class Book extends Item {

    private String author;
    private String isbn;

}
