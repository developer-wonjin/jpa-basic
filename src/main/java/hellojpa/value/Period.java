package hellojpa.value;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Period {
    //기간
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
