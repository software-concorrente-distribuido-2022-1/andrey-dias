package log.repository;

import java.util.ArrayList;
import java.util.List;
import log.domain.LogOperation;

public class MemoryLogRepository implements LogRepository {
    private final List<LogOperation> operations = new ArrayList<>();

    @Override
    public synchronized void save(LogOperation operation) {
        this.operations.add(operation);
    }
}
