package crud.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import crud.domain.Beer;
import database.Database;

public class PostgresBeerRepository implements BeerRepository {
    private static final String SELECT_FIND_BY_ID =
            "SELECT id, name, price FROM beers WHERE id = ?";

    private static final String SELECT_FIND_BY_NAME =
            "SELECT id, name, price FROM beers WHERE name = ?";

    private static final String INSERT_BEER =
            "INSERT INTO beers (name, price) VALUES (?,?)";

    private static final String UPDATE_BEER =
            "UPDATE beers SET name = ?, price = ? WHERE id = ?";

    private static final String DELETE_BEER =
            "DELETE FROM beers WHERE id = ?";

    @Override
    public void create(String name, BigDecimal price) throws SQLException {
        Connection conn = Database.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(INSERT_BEER);

        preparedStatement.setString(1, name);
        preparedStatement.setBigDecimal(2, price);

        preparedStatement.execute();
    }

    @Override
    public Optional<Beer> read(Long id) {
        try {
            Connection conn = Database.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_FIND_BY_ID);

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long currentId = resultSet.getInt("id");
                var currentEmail = resultSet.getString("name");
                var price = resultSet.getBigDecimal("price");

                var beer = new Beer(currentId, currentEmail, price);
                return Optional.of(beer);
            }
            return Optional.empty();

        } catch (Exception e) {
            System.out.println("Error on retrieve beer by id");
            return Optional.empty();
        }
    }

    @Override
    public void update(Long id, String name, BigDecimal price) {
        try {
            Connection conn = Database.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_BEER);

            preparedStatement.setString(1, name);
            preparedStatement.setBigDecimal(2, price);
            preparedStatement.setLong(3, id);

            preparedStatement.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error on updating beer");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Connection conn = Database.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_BEER);

            preparedStatement.setLong(1, id);

            preparedStatement.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error on deleting beer");
        }
    }

    @Override
    public Optional<Beer> findByName(String name) {
        try {
            Connection conn = Database.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_FIND_BY_NAME);

            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long currentId = resultSet.getInt("id");
                var currentEmail = resultSet.getString("name");
                var price = resultSet.getBigDecimal("price");

                var beer = new Beer(currentId, currentEmail, price);
                return Optional.of(beer);
            }
            return Optional.empty();

        } catch (Exception e) {
            System.out.println("Error on retrieve beer by name");
            return Optional.empty();
        }
    }
}
