package login.repository;

import java.sql.SQLException;
import java.util.Optional;
import login.domain.User;

public interface UserRepository {
    Optional<User> findByEmail(String email) throws SQLException;
}
