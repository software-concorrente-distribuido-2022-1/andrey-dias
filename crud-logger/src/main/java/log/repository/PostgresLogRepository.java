package log.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import database.Database;
import log.domain.LogOperation;

public class PostgresLogRepository implements LogRepository {
    private static final String INSERT_LOG_WITH_USER_ID =
            "INSERT INTO logs (user_id, message, done_at) VALUES (?,?,?)";

    private static final String INSERT_LOG =
            "INSERT INTO logs (message, done_at) VALUES (?,?)";

    @Override
    public void save(LogOperation operation) {
        try {
            Connection conn = Database.getConnection();
            PreparedStatement preparedStatement;

            if (operation.getUserId() != null) {
                preparedStatement = conn.prepareStatement(INSERT_LOG_WITH_USER_ID);
                preparedStatement.setLong(1, operation.getUserId());
                preparedStatement.setString(2, operation.getMessage());
                preparedStatement.setObject(3, operation.getDoneAt());
            } else {
                preparedStatement = conn.prepareStatement(INSERT_LOG);
                preparedStatement.setString(1, operation.getMessage());
                preparedStatement.setObject(2, operation.getDoneAt());
            }

            preparedStatement.execute();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage() + " Msg: " + operation.getMessage());
            System.out.println("Error on creating log");
        }
    }
}
