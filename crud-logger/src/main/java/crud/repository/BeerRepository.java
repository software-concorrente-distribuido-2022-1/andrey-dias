package crud.repository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;
import crud.domain.Beer;

public interface BeerRepository {
    void create(String name, BigDecimal price) throws SQLException;
    Optional<Beer> read(Long id);
    void update(Long id, String name, BigDecimal price);
    void delete(Long id);
    Optional<Beer> findByName(String name);
}
