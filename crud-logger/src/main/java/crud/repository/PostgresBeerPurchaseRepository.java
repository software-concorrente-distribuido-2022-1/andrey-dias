package crud.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import database.Database;

public class PostgresBeerPurchaseRepository implements BeerPurchaseRepository {

    private static final String INSERT_PURCHASE_BEER =
            "INSERT INTO beer_purchase (user_id, beer_id, total_value) VALUES (?,?,?)";

    @Override
    public void create(Long userId, Long beerId, BigDecimal totalValue) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(INSERT_PURCHASE_BEER);

        preparedStatement.setLong(1, userId);
        preparedStatement.setLong(2, beerId);
        preparedStatement.setBigDecimal(3, totalValue);

        preparedStatement.execute();
    }
}
