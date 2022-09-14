package crud.repository;

import java.math.BigDecimal;
import java.sql.SQLException;

public interface BeerPurchaseRepository {
    void create(Long userId, Long beerId, BigDecimal totalValue) throws SQLException;
}
