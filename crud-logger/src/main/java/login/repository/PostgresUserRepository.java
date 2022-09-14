package login.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import database.Database;
import login.domain.User;
import login.domain.UserType;

public class PostgresUserRepository implements UserRepository {

    private static final String SELECT_FIND_BY_EMAIL =
            "SELECT id, email, password, type FROM users WHERE email = ?";

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            Connection conn = Database.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_FIND_BY_EMAIL);

            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getInt("id");
                var currentEmail = resultSet.getString("email");
                var password = resultSet.getString("password");
                var type = resultSet.getString("type");

                var user = new User(id, currentEmail, password, UserType.valueOf(type));
                return Optional.of(user);
            }
            return Optional.empty();

        } catch (Exception e) {
            System.out.println("Error on retrieve user by e-mail");
            return Optional.empty();
        }
    }

}
