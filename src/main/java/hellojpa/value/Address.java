package hellojpa.value;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Address {
    //주소
    private String city;
    private String street;
    private String zipcode;

}
