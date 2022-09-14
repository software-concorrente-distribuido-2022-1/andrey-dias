package log.repository;

import log.domain.LogOperation;

public interface LogRepository {
    void save(LogOperation operation);
}
