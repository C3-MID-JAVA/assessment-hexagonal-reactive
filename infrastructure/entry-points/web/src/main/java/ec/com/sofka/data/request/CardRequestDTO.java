package ec.com.sofka.data.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardRequestDTO {
    private int id;
    private String cardNumber;
    private String cardType;
    private LocalDateTime expirationDate;
    private String cvv;
}
