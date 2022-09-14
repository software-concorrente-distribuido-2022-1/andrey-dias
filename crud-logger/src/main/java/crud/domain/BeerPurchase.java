package crud.domain;


import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeerPurchase {
    private Long id;
    private Long beerId;
    private Long userId;
    private BigDecimal totalValue;
}
